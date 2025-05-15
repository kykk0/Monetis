package com.example.monetis.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.domain.repository.ExpenseRepository
import com.example.monetis.domain.usecase.GetExpenseByIdUseCase
import com.example.monetis.domain.usecase.UpdateExpenseUseCase
import com.example.monetis.presentation.viewmodel.EditExpenseViewModel

class EditExpenseViewModelFactory(
    private val expenseRepository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditExpenseViewModel::class.java)) {
            val getExpenseByIdUseCase = GetExpenseByIdUseCase(expenseRepository)
            val updateExpenseUseCase = UpdateExpenseUseCase(expenseRepository)
            return EditExpenseViewModel(getExpenseByIdUseCase, updateExpenseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
