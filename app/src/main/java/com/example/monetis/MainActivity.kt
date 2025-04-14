package com.example.monetis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monetis.presentation.activity.ExpenseActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ExpenseActivity::class.java)
        startActivity(intent)
        finish()
    }
}
