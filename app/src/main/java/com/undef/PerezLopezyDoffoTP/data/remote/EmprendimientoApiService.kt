package com.undef.PerezLopezyDoffoTP.data.remote

import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EmprendimientoApiService {
    @GET("emprendimientos")
    suspend fun getEmprendimientos(): List<Emprendimiento>

    @GET("emprendimientos/{id}")
    suspend fun getEmprendimiento(@Path("id") id: Int): Emprendimiento

    @PATCH("emprendimientos/{id}")
    suspend fun updateFavorite(
        @Path("id") id: Int,
        @Body payload: Map<String, @JvmSuppressWildcards Any>
    ): Emprendimiento
}
