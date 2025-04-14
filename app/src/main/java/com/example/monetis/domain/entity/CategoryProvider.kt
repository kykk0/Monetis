package com.example.monetis.domain.entity

import com.example.monetis.R

object CategoryProvider {

    val categories = listOf(
        ExpenseCategory("1", "Дети", R.drawable.ic_kids, R.drawable.ic_kids_background),
        ExpenseCategory("2", "Дом, уют", R.drawable.ic_home, R.drawable.ic_home_background),
        ExpenseCategory("3", "Забота о себе", R.drawable.ic_selfcare, R.drawable.ic_selfcare_background),
        ExpenseCategory("4", "Зарплата", R.drawable.ic_salary, R.drawable.ic_salary_background),
        ExpenseCategory("5", "Здоровье", R.drawable.ic_health, R.drawable.ic_health_background),
        ExpenseCategory("6", "Кафе и рестораны", R.drawable.ic_cafe, R.drawable.ic_cafe_background),
        ExpenseCategory("7", "Коммуналка", R.drawable.ic_utility, R.drawable.ic_utility_background),
        ExpenseCategory("8", "Корректировка", R.drawable.ic_adjust, R.drawable.ic_adjust_background),
        ExpenseCategory("9", "Машина", R.drawable.ic_car, R.drawable.ic_car_background),
        ExpenseCategory("10", "Образование", R.drawable.ic_education, R.drawable.ic_education_background),
        ExpenseCategory("11", "Платежи, комиссии", R.drawable.ic_commission, R.drawable.ic_commission_background),
        ExpenseCategory("12", "Подарки", R.drawable.ic_gift, R.drawable.ic_gift_background),
        ExpenseCategory("13", "Подписки", R.drawable.ic_subscribe, R.drawable.ic_subscribe_background),
        ExpenseCategory("14", "Покупки", R.drawable.ic_shop, R.drawable.ic_shop_background),
        ExpenseCategory("15", "Продукты", R.drawable.ic_grocery, R.drawable.ic_grocery_background),
        ExpenseCategory("16", "Путешествия", R.drawable.ic_travel, R.drawable.ic_travel_background),
        ExpenseCategory("17", "Развлечения", R.drawable.ic_fun, R.drawable.ic_fun_background),
        ExpenseCategory("18", "Транспорт", R.drawable.ic_transport, R.drawable.ic_transport_background)
    )
}