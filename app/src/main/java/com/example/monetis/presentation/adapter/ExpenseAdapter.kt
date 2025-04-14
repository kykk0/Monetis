package com.example.monetis.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monetis.R
import com.example.monetis.domain.entity.Expense
import android.widget.ImageView
import android.widget.TextView

class ExpenseAdapter(private val onExpenseClick: (Expense) -> Unit) :
    ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    private val categoryResources = mapOf(
        "Дети" to Pair(R.drawable.ic_kids, R.drawable.ic_kids_background),
        "Дом, уют" to Pair(R.drawable.ic_home, R.drawable.ic_home_background),
        "Забота о себе" to Pair(R.drawable.ic_selfcare, R.drawable.ic_selfcare_background),
        "Зарплата" to Pair(R.drawable.ic_salary, R.drawable.ic_salary_background),
        "Здоровье" to Pair(R.drawable.ic_health, R.drawable.ic_health_background),
        "Кафе и рестораны" to Pair(R.drawable.ic_cafe, R.drawable.ic_cafe_background),
        "Коммуналка" to Pair(R.drawable.ic_utility, R.drawable.ic_utility_background),
        "Корректировка" to Pair(R.drawable.ic_adjust, R.drawable.ic_adjust_background),
        "Машина" to Pair(R.drawable.ic_car, R.drawable.ic_car_background),
        "Образование" to Pair(R.drawable.ic_education, R.drawable.ic_education_background),
        "Платежи, комиссии" to Pair(R.drawable.ic_commission, R.drawable.ic_commission_background),
        "Подарки" to Pair(R.drawable.ic_gift, R.drawable.ic_gift_background),
        "Подписки" to Pair(R.drawable.ic_subscribe, R.drawable.ic_subscribe_background),
        "Покупки" to Pair(R.drawable.ic_shop, R.drawable.ic_shop_background),
        "Продукты" to Pair(R.drawable.ic_grocery, R.drawable.ic_grocery_background),
        "Путешествия" to Pair(R.drawable.ic_travel, R.drawable.ic_travel_background),
        "Развлечения" to Pair(R.drawable.ic_fun, R.drawable.ic_fun_background),
        "Транспорт" to Pair(R.drawable.ic_transport, R.drawable.ic_transport_background)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        holder.bind(expense)
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val categoryIconImageView: ImageView = itemView.findViewById(R.id.categoryIconImageView)

        fun bind(expense: Expense) {
            amountTextView.text = expense.amount.toString()
            categoryTextView.text = expense.category
            dateTextView.text = expense.date

            val category = expense.category
            val resources = categoryResources[category]

            resources?.let {
                categoryIconImageView.setImageResource(it.first)
                categoryIconImageView.setBackgroundResource(it.second)
            } ?: run {
                categoryIconImageView.setImageResource(R.drawable.ic_default)
                categoryIconImageView.setBackgroundResource(R.drawable.ic_default_background)
            }

            itemView.setOnClickListener {
                onExpenseClick(expense)
            }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}
