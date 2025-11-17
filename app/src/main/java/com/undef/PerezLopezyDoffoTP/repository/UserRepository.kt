package com.undef.PerezLopezyDoffoTP.repository

import android.content.Context
import android.content.SharedPreferences
import com.undef.PerezLopezyDoffoTP.data.model.CreateUserRequest
import com.undef.PerezLopezyDoffoTP.data.model.User
import com.undef.PerezLopezyDoffoTP.data.remote.NetworkModule
import com.undef.PerezLopezyDoffoTP.data.remote.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest

object UserRepository {
    private const val PREFS_NAME = "user_session"
    private const val KEY_REMEMBER_EMAIL = "remember_email"
    private const val KEY_REMEMBER_PASSWORD = "remember_password"
    private const val KEY_REMEMBER_ENABLED = "remember_enabled"

    private lateinit var api: UserApiService
    private lateinit var prefs: SharedPreferences
    private var currentUserId: Int? = null

    fun initialize(context: Context) {
        if (::api.isInitialized && ::prefs.isInitialized) return
        api = NetworkModule.createUserApi()
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    suspend fun login(email: String, password: String, rememberMe: Boolean): User =
        withContext(Dispatchers.IO) {
        val remoteUsers = api.getUsers(email = email)
        val hashedPassword = hash(password)
        val user = remoteUsers.firstOrNull { it.passwordHash == hashedPassword }
            ?: throw IllegalArgumentException("Credenciales inválidas")
        saveSession(user.id, rememberMe, email, password)
        user
    }

    suspend fun register(username: String, email: String, password: String): User =
        withContext(Dispatchers.IO) {
            val existing = api.getUsers(email = email)
            if (existing.isNotEmpty()) {
                throw IllegalArgumentException("El email ya se encuentra registrado")
            }
            val request = CreateUserRequest(
                username = username,
                email = email,
                passwordHash = hash(password)
            )
            val created = api.createUser(request)
            saveSession(created.id, rememberMe = false, email = email, password = password)
            created
        }

    suspend fun updateUser(
        userId: Int,
        username: String,
        email: String,
        password: String?
    ): User = withContext(Dispatchers.IO) {
        val updates = mutableMapOf<String, Any>(
            "username" to username,
            "email" to email
        )
        if (!password.isNullOrBlank()) {
            updates["passwordHash"] = hash(password)
        }
        api.updateUser(userId, updates)
    }

    suspend fun getUser(userId: Int): User = withContext(Dispatchers.IO) {
        api.getUser(userId)
    }

    fun getCurrentUserId(): Int? = currentUserId

    fun logout(clearRememberedCredentials: Boolean = false) {
        currentUserId = null
        if (!::prefs.isInitialized) return
        if (clearRememberedCredentials) {
            prefs.edit()
                .putBoolean(KEY_REMEMBER_ENABLED, false)
                .remove(KEY_REMEMBER_EMAIL)
                .remove(KEY_REMEMBER_PASSWORD)
                .apply()
        }
    }

    private fun saveSession(userId: Int, rememberMe: Boolean, email: String, password: String) {
        currentUserId = userId
        if (!::prefs.isInitialized) return
        prefs.edit().apply {
            if (rememberMe) {
                putBoolean(KEY_REMEMBER_ENABLED, true)
                putString(KEY_REMEMBER_EMAIL, email)
                putString(KEY_REMEMBER_PASSWORD, password)
            } else {
                putBoolean(KEY_REMEMBER_ENABLED, false)
                remove(KEY_REMEMBER_EMAIL)
                remove(KEY_REMEMBER_PASSWORD)
            }
        }.apply()
        BiometricAuthManager.saveCredentials(email, password)
    }

    fun getRememberedCredentials(): RememberedCredentials? {
        if (!::prefs.isInitialized) return null
        if (!prefs.getBoolean(KEY_REMEMBER_ENABLED, false)) return null
        val email = prefs.getString(KEY_REMEMBER_EMAIL, null) ?: return null
        val password = prefs.getString(KEY_REMEMBER_PASSWORD, null) ?: return null
        return RememberedCredentials(email, password)
    }

    data class RememberedCredentials(
        val email: String,
        val password: String
    )

    private fun hash(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
