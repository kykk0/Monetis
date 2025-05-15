package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.usecase.GetExpenseByIdUseCase
import com.example.monetis.domain.usecase.UpdateExpenseUseCase
import kotlinx.coroutines.launch
import java.math.BigDecimal

class EditExpenseViewModel(
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    private val _expenseDetail = MutableLiveData<Expense?>()
    val expenseDetail: LiveData<Expense?> = _expenseDetail

    fun getExpenseById(id: String) {
        viewModelScope.launch {
            val expense = getExpenseByIdUseCase(id)
            _expenseDetail.postValue(expense)
        }
    }

    fun updateExpense(
        id: String,
        amount: BigDecimal,
        category: String,
        description: String?,
        date: String
    ) {
        val expense = Expense(id, amount, category, description, date)
        viewModelScope.launch {
            updateExpenseUseCase(expense)
        }
    }
}
