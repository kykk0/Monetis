package com.example.monetis.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.domain.repository.ExpenseRepository
import com.example.monetis.domain.usecase.GetExpensesByDateUseCase
import com.example.monetis.presentation.viewmodel.ExpenseListViewModel

class ExpenseListViewModelFactory(
    private val expenseRepository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseListViewModel::class.java)) {
            val getExpensesByDateUseCase = GetExpensesByDateUseCase(expenseRepository)
            return ExpenseListViewModel(getExpensesByDateUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
