package com.example.monetis.data.repository

import android.util.Log
import com.example.monetis.data.network.GigaChatRetrofit
import com.example.monetis.data.dto.ChatRequest
import Message
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository
import java.math.BigDecimal
import java.util.*

class GigaChatRepository(
    private val clientId: String,
    private val clientSecret: String,
    private val expenseRepository: ExpenseRepository,
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

    suspend fun sendMessageWithExpenses(userMessage: String): String {
        val token = getAccessToken()
        val expenses = expenseRepository.getAllExpenses()

        val expensesContext = buildExpensesContext(expenses)

        val messages = listOf(
            Message(role = "user", content = "Ты личный финансовый советник. Ответь, опираясь на список трат."),
            Message(role = "user", content = expensesContext),
            Message(role = "user", content = userMessage)
        )

        val request = ChatRequest(
            model = "GigaChat-2",
            messages = messages
        )

        val response = GigaChatRetrofit.api.sendMessage(
            bearer = "Bearer $token",
            rqUid = UUID.randomUUID().toString(),
            request = request
        )

        return response.choices.firstOrNull()?.message?.content ?: "Нет ответа"
    }


    private fun buildExpensesContext(expenses: List<Expense>): String {
        return buildString {
            append("Ниже список расходов пользователя:\n")
            expenses.forEach {
                append("${it.date}; ${it.category}; ${it.amount.setScale(2, BigDecimal.ROUND_HALF_UP)}\n")
            }
        }
    }
}
