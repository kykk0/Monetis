package com.example.monetis.data.dto

import Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("choices") val choices: List<Choice>
)

@Serializable
data class Choice(
    @SerialName("message") val message: Message
)
