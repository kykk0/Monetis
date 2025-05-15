package com.example.monetis.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monetis.R
import com.example.monetis.presentation.adapter.ExpenseAdapter
import com.example.monetis.presentation.viewmodel.ExpenseListViewModel
import com.example.monetis.presentation.factory.ExpenseListViewModelFactory
import com.example.monetis.data.repository.ExpenseRepositoryImpl
import com.example.monetis.data.datasource.local.AppDatabase
import com.example.monetis.domain.entity.Expense
import java.util.Calendar

class ExpenseListFragment : Fragment() {

    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var fromDateButton: Button
    private lateinit var toDateButton: Button

    private var fromDate: String? = null
    private var toDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_expense_list, container, false)

        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val expenseRepository = ExpenseRepositoryImpl(db.expenseDao())

        val factory = ExpenseListViewModelFactory(expenseRepository)
        expenseListViewModel = ViewModelProvider(this, factory)[ExpenseListViewModel::class.java]

        recyclerView = binding.findViewById(R.id.recyclerView)
        expenseAdapter = ExpenseAdapter { expense -> onExpenseClicked(expense) }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = expenseAdapter

        fromDateButton = binding.findViewById(R.id.fromDateButton)
        toDateButton = binding.findViewById(R.id.toDateButton)

        fromDateButton.setOnClickListener {
            showDatePicker { date ->
                fromDate = date
                fromDateButton.text = "С: $date"
                loadFilteredExpenses()
            }
        }

        toDateButton.setOnClickListener {
            showDatePicker { date ->
                toDate = date
                toDateButton.text = "По: $date"
                loadFilteredExpenses()
            }
        }

        expenseListViewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses)
        }

        expenseListViewModel.getExpenses()

        return binding
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val formattedDate = "$dayOfMonth/${month + 1}/$year"
                onDateSelected(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    private fun loadFilteredExpenses() {
        expenseListViewModel.getExpenses(fromDate, toDate)
    }

    private fun onExpenseClicked(expense: Expense) {
        val bundle = Bundle().apply { putString("expenseId", expense.id) }
        val expenseDetailFragment = ExpenseDetailFragment().apply { arguments = bundle }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, expenseDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}
