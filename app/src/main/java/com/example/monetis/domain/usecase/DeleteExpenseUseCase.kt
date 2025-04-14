package com.example.monetis.domain.usecase

import com.example.monetis.domain.repository.ExpenseRepository

class DeleteExpenseUseCase(private val expenseRepository: ExpenseRepository) {

    suspend operator fun invoke(id: String) {
        expenseRepository.deleteExpenseById(id)
    }
}
