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
    private const val KEY_USER_ID = "current_user_id"

    private lateinit var api: UserApiService
    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        if (::api.isInitialized && ::prefs.isInitialized) return
        api = NetworkModule.createUserApi()
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    suspend fun login(email: String, password: String): User = withContext(Dispatchers.IO) {
        val remoteUsers = api.getUsers(email = email)
        val hashedPassword = hash(password)
        val user = remoteUsers.firstOrNull { it.passwordHash == hashedPassword }
            ?: throw IllegalArgumentException("Credenciales inválidas")
        saveSession(user.id)
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
            saveSession(created.id)
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

    fun hasSession(): Boolean = getCurrentUserId() != null

    fun getCurrentUserId(): Int? {
        if (!::prefs.isInitialized) return null
        val storedId = prefs.getInt(KEY_USER_ID, -1)
        return if (storedId == -1) null else storedId
    }

    fun logout() {
        if (!::prefs.isInitialized) return
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    private fun saveSession(userId: Int) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
    }

    private fun hash(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
