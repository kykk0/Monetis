package com.example.monetis.data.repository

import Message
import com.example.monetis.data.dto.ChatRequest
import com.example.monetis.data.network.GigaChatRetrofit
import java.util.*

class GigaChatRepository(
    private val clientId: String,
    private val clientSecret: String
) {

    private var accessToken: String? = null

    private suspend fun getAccessToken(): String {
        if (accessToken != null) return accessToken!!
        val encodedAuth = android.util.Base64.encodeToString(
            "$clientId:$clientSecret".toByteArray(),
            android.util.Base64.NO_WRAP
        )
        val response = GigaChatRetrofit.api.getAccessToken(
            auth = "Basic $encodedAuth",
            rqUid = UUID.randomUUID().toString()
        )
        accessToken = response.accessToken
        return accessToken!!
    }

    suspend fun sendMessage(message: String): String {
        val token = getAccessToken()
        val response = GigaChatRetrofit.api.sendMessage(
            bearer = "Bearer $token",
            rqUid = UUID.randomUUID().toString(),
            request = ChatRequest(
                messages = listOf(Message(role = "user", content = message))
            )
        )
        return response.choices.firstOrNull()?.message?.content ?: "Нет ответа"
    }
}
