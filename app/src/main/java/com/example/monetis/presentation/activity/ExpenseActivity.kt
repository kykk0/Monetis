package com.example.monetis.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monetis.R
import com.example.monetis.presentation.fragment.ExpenseListFragment
import com.example.monetis.presentation.fragment.AddExpenseFragment

class ExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        if (savedInstanceState == null) {
            val expenseListFragment = ExpenseListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, expenseListFragment)
                .commit()
        }
    }

    fun showAddExpenseFragment() {
        val addExpenseFragment = AddExpenseFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, addExpenseFragment)
            .addToBackStack(null)
            .commit()
    }
}
