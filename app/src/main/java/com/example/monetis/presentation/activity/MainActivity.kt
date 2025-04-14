package com.example.monetis.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monetis.R
import com.example.monetis.presentation.fragment.AddExpenseFragment
import com.example.monetis.presentation.fragment.ExpenseListFragment
import com.example.monetis.presentation.fragment.AIChatFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ExpenseListFragment())
                .commit()
        }

        val addExpenseButton = findViewById<View>(R.id.addExpenseButton)
        val expensesListButton = findViewById<View>(R.id.expensesListButton)
        val aiChatButton = findViewById<View>(R.id.aiChatButton)

        addExpenseButton.setOnClickListener {
            openFragment(AddExpenseFragment())
        }

        expensesListButton.setOnClickListener {
            openFragment(ExpenseListFragment())
        }

        aiChatButton.setOnClickListener {
            openFragment(AIChatFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

}

