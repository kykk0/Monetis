package com.example.monetis.domain.entity

import java.math.BigDecimal

data class Expense(
    val id: String,
    val amount: BigDecimal,
    val category: String,
    val description: String?,
    val date: String
)
