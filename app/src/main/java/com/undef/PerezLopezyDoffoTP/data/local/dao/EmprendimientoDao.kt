package com.undef.PerezLopezyDoffoTP.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.undef.PerezLopezyDoffoTP.data.local.entity.EmprendimientoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmprendimientoDao {
    @Query("SELECT * FROM emprendimientos ORDER BY name")
    fun observeEmprendimientos(): Flow<List<EmprendimientoEntity>>

    @Query("SELECT * FROM emprendimientos WHERE id = :id LIMIT 1")
    fun observeById(id: Int): Flow<EmprendimientoEntity?>

    @Query(
        """
        SELECT * FROM emprendimientos 
        WHERE emprendedor_id = :emprendedorId 
          AND (:excludeId IS NULL OR id != :excludeId)
        ORDER BY name
        """
    )
    fun observeByEmprendedor(
        emprendedorId: Int,
        excludeId: Int?
    ): Flow<List<EmprendimientoEntity>>

    @Query(
        """
        SELECT * FROM emprendimientos 
        WHERE name LIKE '%' || :query || '%'
           OR category LIKE '%' || :query || '%'
           OR emprendedor_name LIKE '%' || :query || '%'
        ORDER BY name
        """
    )
    fun observeFiltered(query: String): Flow<List<EmprendimientoEntity>>

    @Query("SELECT * FROM emprendimientos WHERE isFav = 1 ORDER BY name")
    fun observeFavorites(): Flow<List<EmprendimientoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EmprendimientoEntity>)

    @Query("UPDATE emprendimientos SET isFav = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFav: Boolean)

    @Query("SELECT * FROM emprendimientos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EmprendimientoEntity?

    @Query("SELECT COUNT(*) FROM emprendimientos")
    suspend fun count(): Int
}
