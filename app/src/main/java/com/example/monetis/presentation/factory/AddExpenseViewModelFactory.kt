package com.example.monetis.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.domain.repository.ExpenseRepository
import com.example.monetis.domain.usecase.AddExpenseUseCase
import com.example.monetis.presentation.viewmodel.AddExpenseViewModel

class AddExpenseViewModelFactory(
    private val expenseRepository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val addExpenseUseCase = AddExpenseUseCase(expenseRepository)
            return AddExpenseViewModel(addExpenseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
