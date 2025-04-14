package com.example.monetis.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.domain.repository.ExpenseRepository
import com.example.monetis.domain.usecase.GetExpenseByIdUseCase
import com.example.monetis.domain.usecase.DeleteExpenseUseCase
import com.example.monetis.presentation.viewmodel.ExpenseDetailViewModel

class ExpenseDetailViewModelFactory(
    private val expenseRepository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseDetailViewModel::class.java)) {
            val getExpenseByIdUseCase = GetExpenseByIdUseCase(expenseRepository)
            val deleteExpenseUseCase = DeleteExpenseUseCase(expenseRepository)
            return ExpenseDetailViewModel(getExpenseByIdUseCase, deleteExpenseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
