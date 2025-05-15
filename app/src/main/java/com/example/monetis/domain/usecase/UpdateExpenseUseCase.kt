package com.example.monetis.domain.usecase

import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository

class UpdateExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) {
        repository.updateExpense(expense)
    }
}
