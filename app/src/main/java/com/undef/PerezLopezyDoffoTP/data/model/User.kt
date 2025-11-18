package com.undef.PerezLopezyDoffoTP.data.model

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
    val bio: String? = null,
    val profileImage: String? = null
)

data class CreateUserRequest(
    val username: String,
    val email: String,
    val passwordHash: String,
    val bio: String? = null,
    val profileImage: String? = null
)
