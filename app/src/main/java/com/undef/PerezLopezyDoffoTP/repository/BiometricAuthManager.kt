package com.undef.PerezLopezyDoffoTP.repository

import android.content.Context
import android.content.SharedPreferences

/**
 * Persiste las credenciales necesarias para realizar login biométrico y saber
 * si el usuario habilitó la opción desde configuraciones.
 */
object BiometricAuthManager {
    private const val PREFS_NAME = "biometric_login"
    private const val KEY_ENABLED = "enabled"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        if (::prefs.isInitialized) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isEnabled(): Boolean =
        if (::prefs.isInitialized) prefs.getBoolean(KEY_ENABLED, false) else false

    fun setEnabled(enabled: Boolean) {
        ensurePrefs()
        prefs.edit()
            .putBoolean(KEY_ENABLED, enabled)
            .apply()
    }

    fun saveCredentials(email: String, password: String) {
        ensurePrefs()
        prefs.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun getCredentials(): UserRepository.RememberedCredentials? {
        if (!::prefs.isInitialized) return null
        val email = prefs.getString(KEY_EMAIL, null) ?: return null
        val password = prefs.getString(KEY_PASSWORD, null) ?: return null
        if (email.isBlank() || password.isBlank()) return null
        return UserRepository.RememberedCredentials(email, password)
    }

    fun hasCredentials(): Boolean = getCredentials() != null

    private fun ensurePrefs() {
        if (!::prefs.isInitialized) {
            throw IllegalStateException("BiometricAuthManager must be initialized before use")
        }
    }
}
