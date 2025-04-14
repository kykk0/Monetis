package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ExpenseListFragment : Fragment() {

    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        expenseListViewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses)
        }

        expenseListViewModel.getExpenses()

        return binding
    }

    private fun onExpenseClicked(expense: Expense) {
        val bundle = Bundle().apply {
            putString("expenseId", expense.id)
        }

        val expenseDetailFragment = ExpenseDetailFragment().apply {
            arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, expenseDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}
