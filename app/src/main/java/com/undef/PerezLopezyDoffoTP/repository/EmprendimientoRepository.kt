package com.undef.PerezLopezyDoffoTP.repository

import android.content.Context
import androidx.room.Room
import com.undef.PerezLopezyDoffoTP.data.local.ManosLocalesDatabase
import com.undef.PerezLopezyDoffoTP.data.local.dao.EmprendimientoDao
import com.undef.PerezLopezyDoffoTP.data.local.entity.EmprendimientoEntity
import com.undef.PerezLopezyDoffoTP.data.local.entity.UserFavoriteEntity
import com.undef.PerezLopezyDoffoTP.data.local.entity.toDomain
import com.undef.PerezLopezyDoffoTP.data.local.entity.toEntity
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.data.remote.EmprendimientoApiService
import com.undef.PerezLopezyDoffoTP.data.remote.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Orquesta la sincronización entre Room (cache local) y Retrofit (mock server).
 * Todas las pantallas consumen los flows expuestos acá para recibir datos reactivos.
 */
object EmprendimientoRepository {
    private lateinit var api: EmprendimientoApiService
    private lateinit var dao: EmprendimientoDao
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Inicializa LAZY la BD y el cliente HTTP.
     * Se llama desde Application para que el repo quede listo apenas arranca la app.
     */
    fun initialize(context: Context) {
        if (::dao.isInitialized && ::api.isInitialized) return

        val database = Room.databaseBuilder(
            context.applicationContext,
            ManosLocalesDatabase::class.java,
            "manos_locales.db"
        )
            .addMigrations(ManosLocalesDatabase.MIGRATION_1_2)
            .build()

        dao = database.emprendimientoDao()
        api = NetworkModule.createEmprendimientoApi()

        scope.launch {
            if (dao.count() == 0) {
                refreshEmprendimientos()
            }
        }
    }

    /** Flujos reactivos usados en Home. */
    fun observeEmprendimientos(): Flow<List<Emprendimiento>> =
        dao.observeEmprendimientos().map { entities ->
            entities.toDomainList()
        }

    fun observeFavorites(): Flow<List<Emprendimiento>> =
        dao.observeFavorites().map { entities ->
            entities.toDomainList()
        }

    fun observeFiltered(query: String): Flow<List<Emprendimiento>> =
        dao.observeFiltered(query).map { entities ->
            entities.toDomainList()
        }

    fun observeEmprendimiento(emprendimientoId: Int): Flow<Emprendimiento?> =
        dao.observeById(emprendimientoId).map { it?.toDomain() }

    /** Devuelve los emprendimientos del mismo emprendedor para la sección "Otros productos". */
    fun observeEmprendimientosDelEmprendedor(
        emprendedorId: Int,
        excludeEmprendimientoId: Int? = null
    ): Flow<List<Emprendimiento>> =
        dao.observeByEmprendedor(emprendedorId, excludeEmprendimientoId).map { list ->
            list.toDomainList()
        }

    /** Fuerza un refresh completo desde el mock server y lo guarda en Room. */
    suspend fun refreshEmprendimientos() = withContext(Dispatchers.IO) {
        val remoteEmprendimientos = api.getEmprendimientos()
        dao.insertAll(remoteEmprendimientos.map { it.toEntity() })
        UserRepository.getCurrentUserId()?.let { applyFavoritesForUser(it) }
    }

    /** Refresca solo un detalle para evitar traer toda la lista de nuevo. */
    suspend fun refreshEmprendimiento(emprendimientoId: Int) = withContext(Dispatchers.IO) {
        val remoto = api.getEmprendimiento(emprendimientoId)
        dao.insertAll(listOf(remoto.toEntity()))
        UserRepository.getCurrentUserId()?.let { applyFavoritesForUser(it) }
    }

    /**
     * Alterna el favorito en Room para feedback instantáneo y lo replica en el mock server.
     * Si el PATCH remoto falla se ignora (la próxima sync lo corrige).
     */
    suspend fun setFav(emprendimientoId: Int, userId: Int) = withContext(Dispatchers.IO) {
        val current = dao.getById(emprendimientoId) ?: return@withContext
        val alreadyFavorite = dao.isFavoriteForUser(userId, emprendimientoId)
        if (alreadyFavorite) {
            dao.deleteFavorite(userId, emprendimientoId)
        } else {
            dao.insertFavorite(
                UserFavoriteEntity(
                    userId = userId,
                    emprendimientoId = current.id
                )
            )
        }
        dao.updateFavorite(emprendimientoId, !alreadyFavorite)

        scope.launch {
            runCatching {
                api.updateFavorite(
                    emprendimientoId,
                    mapOf("isFav" to !alreadyFavorite)
                )
            }
        }
    }

    suspend fun syncFavoritesForUser(userId: Int) = withContext(Dispatchers.IO) {
        applyFavoritesForUser(userId)
    }

    private fun List<EmprendimientoEntity>.toDomainList(): List<Emprendimiento> =
        map { it.toDomain() }

    private suspend fun applyFavoritesForUser(userId: Int) {
        val favoriteIds = dao.getFavoriteIds(userId)
        dao.clearFavoriteFlags()
        if (favoriteIds.isNotEmpty()) {
            dao.applyFavoriteFlags(favoriteIds)
        }
    }
}
