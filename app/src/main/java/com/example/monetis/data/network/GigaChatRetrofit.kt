package com.example.monetis.data.network

import com.example.monetis.data.api.GigaChatApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object GigaChatRetrofit {
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ngw.devices.sberbank.ru:9443/api/v2/")
            .client(UnsafeOkHttpClient.get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: GigaChatApi by lazy {
        retrofit.create(GigaChatApi::class.java)
    }
}
