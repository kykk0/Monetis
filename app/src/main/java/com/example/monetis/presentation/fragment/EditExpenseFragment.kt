package com.example.monetis.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.R
import com.example.monetis.presentation.viewmodel.EditExpenseViewModel
import com.example.monetis.presentation.factory.EditExpenseViewModelFactory
import com.example.monetis.data.repository.ExpenseRepositoryImpl
import com.example.monetis.data.datasource.local.AppDatabase
import com.example.monetis.domain.entity.ExpenseCategory
import com.example.monetis.domain.entity.CategoryProvider
import com.example.monetis.presentation.adapter.CategorySpinnerAdapter
import java.math.BigDecimal
import java.util.*

class EditExpenseFragment : Fragment() {

    private lateinit var editExpenseViewModel: EditExpenseViewModel
    private var expenseId: String? = null

    private lateinit var amountEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_edit_expense, container, false)

        expenseId = arguments?.getString("expenseId")

        amountEditText = binding.findViewById(R.id.amountEditText)
        categorySpinner = binding.findViewById(R.id.categorySpinner)
        descriptionEditText = binding.findViewById(R.id.descriptionEditText)
        dateEditText = binding.findViewById(R.id.dateEditText)
        saveButton = binding.findViewById(R.id.saveButton)

        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val expenseRepository = ExpenseRepositoryImpl(db.expenseDao())

        val factory = EditExpenseViewModelFactory(expenseRepository)
        editExpenseViewModel = ViewModelProvider(this, factory)[EditExpenseViewModel::class.java]

        val adapter = CategorySpinnerAdapter(requireContext(), CategoryProvider.categories)
        categorySpinner.adapter = adapter

        expenseId?.let {
            editExpenseViewModel.getExpenseById(it)
            editExpenseViewModel.expenseDetail.observe(viewLifecycleOwner) { expense ->
                expense?.let { exp ->
                    amountEditText.setText(exp.amount.toPlainString())
                    descriptionEditText.setText(exp.description)
                    dateEditText.setText(exp.date)

                    val categoryIndex = CategoryProvider.categories.indexOfFirst { cat -> cat.name == exp.category }
                    if (categoryIndex >= 0) categorySpinner.setSelection(categoryIndex)
                }
            }
        }

        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        saveButton.setOnClickListener {
            val amountStr = amountEditText.text.toString()
            if (amountStr.isEmpty() || amountStr.toBigDecimalOrNull() == null || amountStr.toBigDecimal() < BigDecimal.ZERO) {
                Toast.makeText(context, "Введите корректную сумму (неотрицательное число)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toBigDecimal()
            val category = (categorySpinner.selectedItem as ExpenseCategory).name
            val description = descriptionEditText.text.toString()
            val date = dateEditText.text.toString()

            expenseId?.let { id ->
                editExpenseViewModel.updateExpense(id, amount, category, description, date)
            }

            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding
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
