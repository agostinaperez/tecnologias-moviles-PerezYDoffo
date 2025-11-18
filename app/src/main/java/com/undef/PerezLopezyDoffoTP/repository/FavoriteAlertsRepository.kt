package com.undef.PerezLopezyDoffoTP.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Guarda en SharedPreferences qué emprendedores tienen alertas activas para que
 * el usuario reciba recordatorios cuando haya novedades.
 */
object FavoriteAlertsRepository {
    private const val PREFS_NAME = "favorite_alerts"
    private const val KEY_ALERTED_IDS = "alerted_emprendedores"

    private lateinit var prefs: SharedPreferences

    private val _alertedEntrepreneurs = MutableStateFlow<Set<Int>>(emptySet())
    val alertedEntrepreneurs: StateFlow<Set<Int>> = _alertedEntrepreneurs.asStateFlow()

    fun initialize(context: Context) {
        if (::prefs.isInitialized) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val stored = prefs.getStringSet(KEY_ALERTED_IDS, emptySet()).orEmpty()
        _alertedEntrepreneurs.value = stored.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun setAlert(emprendedorId: Int, enabled: Boolean) {
        val current = _alertedEntrepreneurs.value.toMutableSet()
        if (enabled) {
            current.add(emprendedorId)
        } else {
            current.remove(emprendedorId)
        }
        if (current != _alertedEntrepreneurs.value) {
            _alertedEntrepreneurs.value = current.toSet()
            save(current)
        }
    }

    fun toggle(emprendedorId: Int) {
        val enabled = !_alertedEntrepreneurs.value.contains(emprendedorId)
        setAlert(emprendedorId, enabled)
    }

    private fun save(ids: Set<Int>) {
        val asString = ids.map { it.toString() }.toSet()
        prefs.edit()
            .putStringSet(KEY_ALERTED_IDS, asString)
            .apply()
    }
}
