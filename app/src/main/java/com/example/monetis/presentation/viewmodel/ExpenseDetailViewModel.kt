package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.usecase.GetExpenseByIdUseCase
import com.example.monetis.domain.usecase.DeleteExpenseUseCase
import kotlinx.coroutines.launch

class ExpenseDetailViewModel(
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _expenseDetail = MutableLiveData<Expense>()
    val expenseDetail: LiveData<Expense> get() = _expenseDetail

    fun getExpenseById(id: String) {
        viewModelScope.launch {
            _expenseDetail.value = getExpenseByIdUseCase(id)
        }
    }

    fun deleteExpenseById(id: String) {
        viewModelScope.launch {
            deleteExpenseUseCase(id)
        }
    }
}
