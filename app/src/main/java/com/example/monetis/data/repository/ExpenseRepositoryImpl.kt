package com.example.monetis.data.repository

import com.example.monetis.data.datasource.local.ExpenseDao
import com.example.monetis.data.entity.ExpenseEntity
import com.example.monetis.domain.entity.Expense
import com.example.monetis.domain.repository.ExpenseRepository
import java.math.BigDecimal

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense) {
        val expenseEntity = ExpenseEntity(
            amount = expense.amount.toString(),
            category = expense.category,
            description = expense.description,
            date = expense.date
        )
        expenseDao.insert(expenseEntity)
    }

    override suspend fun getAllExpenses(): List<Expense> {
        return expenseDao.getAllExpenses().map {
            Expense(
                id = it.id,
                amount = BigDecimal(it.amount),
                category = it.category,
                description = it.description,
                date = it.date
            )
        }
    }

    override suspend fun getExpenseById(id: String): Expense {
        val expenseEntity = expenseDao.getExpenseById(id)
            ?: throw IllegalArgumentException("Expense with ID $id not found")

        return Expense(
            id = expenseEntity.id,
            amount = BigDecimal(expenseEntity.amount),
            category = expenseEntity.category,
            description = expenseEntity.description,
            date = expenseEntity.date
        )
    }

    override suspend fun deleteExpenseById(id: String) {
        expenseDao.deleteExpenseById(id)
    }
}
