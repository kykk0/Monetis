package com.example.monetis.data.repository

import android.util.Log
import com.example.monetis.data.network.GigaChatRetrofit
import com.example.monetis.data.dto.ChatRequest
import Message
import java.util.*

class GigaChatRepository(
    private val clientId: String,
    private val clientSecret: String
) {

    private var accessToken: String? = null

    private suspend fun getAccessToken(): String {
        if (accessToken != null) return accessToken!!

        Log.d("GigaChat", "Получаем access token...")

        val encodedAuth = android.util.Base64.encodeToString(
            "$clientId:$clientSecret".toByteArray(),
            android.util.Base64.NO_WRAP
        )

        val response = GigaChatRetrofit.api.getAccessToken(
            auth = "Basic $encodedAuth",
            rqUid = UUID.randomUUID().toString()
        )

        Log.d("GigaChat", "Токен получен: ${response.accessToken}")

        accessToken = response.accessToken
        return accessToken!!
    }

    suspend fun sendMessage(message: String): String {
        val token = getAccessToken()

        Log.d("GigaChat", "Отправляем сообщение: $message")

        val request = ChatRequest(
            model = "GigaChat-2",
            messages = listOf(Message(role = "user", content = message))
        )


        val response = GigaChatRetrofit.api.sendMessage(
            bearer = "Bearer $token",
            rqUid = UUID.randomUUID().toString(),
            request = request
        )

        val reply = response.choices.firstOrNull()?.message?.content ?: "Нет ответа"

        Log.d("GigaChat", "Ответ от модели: $reply")

        return reply
    }
}

