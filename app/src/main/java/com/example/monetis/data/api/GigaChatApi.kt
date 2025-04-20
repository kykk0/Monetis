package com.example.monetis.data.api

import com.example.monetis.data.dto.ChatRequest
import com.example.monetis.data.dto.ChatResponse
import com.example.monetis.data.dto.TokenResponse
import retrofit2.http.*

interface GigaChatApi {

    @FormUrlEncoded
    @POST("api/v2/oauth")
    suspend fun getAccessToken(
        @Field("scope") scope: String = "GIGACHAT_API_PERS",
        @Header("Authorization") auth: String,
        @Header("RqUID") rqUid: String,
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @Header("Accept") accept: String = "application/json"
    ): TokenResponse

    @POST("api/v1/chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") bearer: String,
        @Header("RqUID") rqUid: String,
        @Body request: ChatRequest
    ): ChatResponse
}
