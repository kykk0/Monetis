package com.example.monetis.domain.usecase

import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository

class GetAllExpensesUseCase(private val expenseRepository: ExpenseRepository) {

    suspend operator fun invoke(): List<Expense> {
        return expenseRepository.getAllExpenses()
    }
}
