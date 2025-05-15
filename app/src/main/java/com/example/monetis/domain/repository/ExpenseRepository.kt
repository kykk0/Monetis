package com.example.monetis.domain.repository

import com.example.monetis.domain.entity.Expense

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense)
    suspend fun getAllExpenses(): List<Expense>
    suspend fun getExpenseById(id: String): Expense
    suspend fun deleteExpenseById(id: String)
    suspend fun updateExpense(expense: Expense)
}
