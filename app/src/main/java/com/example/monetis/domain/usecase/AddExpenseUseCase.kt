package com.example.monetis.domain.usecase

import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository

class AddExpenseUseCase(private val expenseRepository: ExpenseRepository) {

    suspend operator fun invoke(expense: Expense) {
        expenseRepository.addExpense(expense)
    }
}
