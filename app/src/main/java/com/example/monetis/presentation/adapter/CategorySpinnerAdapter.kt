package com.example.monetis.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.monetis.R
import com.example.monetis.domain.entity.ExpenseCategory

class CategorySpinnerAdapter(
    context: Context,
    private val categories: List<ExpenseCategory>
) : ArrayAdapter<ExpenseCategory>(context, R.layout.item_category_spinner, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent, false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent, true)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup, isDropDown: Boolean): View {
        val view = convertView ?: View.inflate(context, R.layout.item_category_spinner, null)

        val icon = view.findViewById<ImageView>(R.id.categoryIcon)
        val label = view.findViewById<TextView>(R.id.categoryName)

        val category = categories[position]
        icon.setImageResource(category.iconResId)
        icon.setBackgroundResource(category.backgroundResId)
        label.text = category.name

        return view
    }
}
