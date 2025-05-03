package com.example.monetis.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monetis.R
import com.example.monetis.presentation.fragment.AddExpenseFragment
import com.example.monetis.presentation.fragment.ExpenseListFragment
import com.example.monetis.presentation.fragment.AIChatFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (savedInstanceState == null) {
            val startFragment = if (currentUser != null) {
                ExpenseListFragment()
            } else {
                com.example.monetis.presentation.fragment.AuthFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, startFragment)
                .commit()
        }

        findViewById<View>(R.id.addExpenseButton).setOnClickListener {
            openFragment(AddExpenseFragment())
        }

        findViewById<View>(R.id.expensesListButton).setOnClickListener {
            openFragment(ExpenseListFragment())
        }

        findViewById<View>(R.id.aiChatButton).setOnClickListener {
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

