package com.example.monetis.data.network

import com.example.monetis.data.api.GigaChatApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

object GigaChatRetrofit {

    private const val BASE_URL = "https://ngw.devices.sberbank.ru:9443/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: GigaChatApi by lazy {
        retrofit.create(GigaChatApi::class.java)
    }
}
