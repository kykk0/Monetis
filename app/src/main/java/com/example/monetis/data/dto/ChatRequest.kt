package com.example.monetis.data.dto

import Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("model") val model: String = "GigaChat",
    @SerialName("messages") val messages: List<Message>
)
