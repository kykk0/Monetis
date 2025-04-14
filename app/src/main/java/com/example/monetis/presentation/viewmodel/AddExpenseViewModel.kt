package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID

class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    fun addExpense(amount: BigDecimal, category: String, description: String?, date: String) {
        val expense = Expense(
            id = UUID.randomUUID().toString(),
            amount = amount,
            category = category,
            description = description,
            date = date
        )

        viewModelScope.launch {
            addExpenseUseCase(expense)
        }
    }
}
