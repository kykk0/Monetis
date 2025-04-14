package com.example.monetis.domain.usecase

import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository

class GetExpenseByIdUseCase(private val expenseRepository: ExpenseRepository) {

    suspend operator fun invoke(id: String): Expense {
        return expenseRepository.getExpenseById(id)
    }
}
