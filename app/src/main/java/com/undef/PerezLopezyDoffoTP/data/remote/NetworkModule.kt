package com.undef.PerezLopezyDoffoTP.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// 10.0.2.2 apunta al localhost de la máquina cuando se corre en el emulador Android.
private const val BASE_URL = "http://10.0.2.2:3000/"

//Construye las dependencias HTTP (OkHttp + Retrofit + Moshi) para hablar con json-server.
object NetworkModule {
    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // Logging básico para depurar requests/responses al mock server.
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .build()
    }

    fun createEmprendimientoApi(): EmprendimientoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EmprendimientoApiService::class.java)
    }
}
