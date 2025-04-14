package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.usecase.GetAllExpensesUseCase
import kotlinx.coroutines.launch

class ExpenseListViewModel(
    private val getAllExpensesUseCase: GetAllExpensesUseCase
) : ViewModel() {

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    fun getExpenses() {
        viewModelScope.launch {
            _expenses.value = getAllExpensesUseCase()
        }
    }
}
