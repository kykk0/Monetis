package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.monetis.R
import com.example.monetis.presentation.viewmodel.ExpenseDetailViewModel
import com.example.monetis.presentation.factory.ExpenseDetailViewModelFactory
import com.example.monetis.data.repository.ExpenseRepositoryImpl
import com.example.monetis.data.datasource.local.AppDatabase

class ExpenseDetailFragment : Fragment() {

    private lateinit var expenseDetailViewModel: ExpenseDetailViewModel
    private var expenseId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_expense_detail, container, false)

        expenseId = arguments?.getString("expenseId")

        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val expenseRepository = ExpenseRepositoryImpl(db.expenseDao())

        val factory = ExpenseDetailViewModelFactory(expenseRepository)
        expenseDetailViewModel = ViewModelProvider(this, factory)[ExpenseDetailViewModel::class.java]

        val amountTextView = binding.findViewById<TextView>(R.id.amountTextView)
        val categoryTextView = binding.findViewById<TextView>(R.id.categoryTextView)
        val descriptionTextView = binding.findViewById<TextView>(R.id.descriptionTextView)
        val dateTextView = binding.findViewById<TextView>(R.id.dateTextView)

        val editButton = binding.findViewById<Button>(R.id.editButton)
        val deleteButton = binding.findViewById<Button>(R.id.deleteButton)

        expenseId?.let {
            expenseDetailViewModel.getExpenseById(it)
            expenseDetailViewModel.expenseDetail.observe(viewLifecycleOwner) { expense ->
                expense?.let { exp ->
                    amountTextView.text = exp.amount.toString()
                    categoryTextView.text = exp.category
                    descriptionTextView.text = exp.description
                    dateTextView.text = exp.date
                }
            }
        }

        editButton.setOnClickListener {
            expenseId?.let { id ->
                val fragment = EditExpenseFragment().apply {
                    arguments = Bundle().apply {
                        putString("expenseId", id)
                    }
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        deleteButton.setOnClickListener {
            expenseId?.let { id ->
                expenseDetailViewModel.deleteExpenseById(id)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ExpenseListFragment())
                    .commit()
            }
        }

        return binding
    }
}
