package com.example.monetis.domain.usecase

import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository
import java.text.SimpleDateFormat
import java.util.*

class GetExpensesByDateUseCase(private val repository: ExpenseRepository) {

    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    suspend operator fun invoke(fromDate: String?, toDate: String?): List<Expense> {
        val allExpenses = repository.getAllExpenses()

        val from = fromDate?.let { sdf.parse(it) }
        val to = toDate?.let { sdf.parse(it) }

        return allExpenses.filter { expense ->
            val expenseDate = sdf.parse(expense.date) ?: return@filter false
            val afterFrom = from == null || !expenseDate.before(from)
            val beforeTo = to == null || !expenseDate.after(to)
            afterFrom && beforeTo
        }
    }
}
