package com.undef.PerezLopezyDoffoTP.data.remote

import com.undef.PerezLopezyDoffoTP.data.model.CreateUserRequest
import com.undef.PerezLopezyDoffoTP.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(@Query("email") email: String? = null): List<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequest): User

    @PATCH("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body updates: Map<String, @JvmSuppressWildcards Any>
    ): User
}
