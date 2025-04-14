package com.example.monetis.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.R
import com.example.monetis.presentation.viewmodel.AddExpenseViewModel
import com.example.monetis.presentation.factory.AddExpenseViewModelFactory
import com.example.monetis.data.repository.ExpenseRepositoryImpl
import com.example.monetis.data.datasource.local.AppDatabase
import com.example.monetis.domain.entity.ExpenseCategory
import com.example.monetis.domain.entity.CategoryProvider
import com.example.monetis.presentation.adapter.CategorySpinnerAdapter
import java.math.BigDecimal
import java.util.*

class AddExpenseFragment : Fragment() {

    private lateinit var addExpenseViewModel: AddExpenseViewModel
    private lateinit var dateEditText: EditText
    private lateinit var categorySpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_add_expense, container, false)

        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val expenseRepository = ExpenseRepositoryImpl(db.expenseDao())

        val factory = AddExpenseViewModelFactory(expenseRepository)
        addExpenseViewModel = ViewModelProvider(this, factory)[AddExpenseViewModel::class.java]

        val amountEditText = binding.findViewById<EditText>(R.id.amountEditText)
        categorySpinner = binding.findViewById(R.id.categorySpinner)
        val descriptionEditText = binding.findViewById<EditText>(R.id.descriptionEditText)
        dateEditText = binding.findViewById(R.id.dateEditText)

        setTodayDate()

        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        val adapter = CategorySpinnerAdapter(requireContext(), CategoryProvider.categories)
        categorySpinner.adapter = adapter

        val addButton = binding.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val amount = BigDecimal(amountEditText.text.toString())
            val selectedCategory = categorySpinner.selectedItem as ExpenseCategory
            val category = selectedCategory.name
            val description = descriptionEditText.text.toString()
            val date = dateEditText.text.toString()

            addExpenseViewModel.addExpense(amount, category, description, date)

            val expenseListFragment = ExpenseListFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, expenseListFragment)
                .addToBackStack(null)
                .commit()
        }

        return binding
    }

    private fun setTodayDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val todayDate = "$day/${month + 1}/$year"
        dateEditText.setText(todayDate)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateEditText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
