package com.example.monetis.data.api

import com.example.monetis.data.dto.ChatRequest
import com.example.monetis.data.dto.ChatResponse
import com.example.monetis.data.dto.TokenResponse
import retrofit2.http.*

interface GigaChatApi {

    @FormUrlEncoded
    @POST("oauth")
    suspend fun getAccessToken(
        @Header("Authorization") auth: String,
        @Header("RqUID") rqUid: String,
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @Header("Accept") accept: String = "application/json",
        @Field("scope") scope: String = "GIGACHAT_API_PERS"
    ): TokenResponse

    @POST("https://gigachat.devices.sberbank.ru/api/v1/chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") bearer: String,
        @Header("RqUID") rqUid: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Accept") accept: String = "application/json",
        @Body request: ChatRequest
    ): ChatResponse
}
