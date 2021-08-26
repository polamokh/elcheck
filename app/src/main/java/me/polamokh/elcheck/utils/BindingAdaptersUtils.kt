package me.polamokh.elcheck.utils

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import me.polamokh.elcheck.R
import java.util.*

@BindingAdapter("expenseName")
fun TextView.bindExpenseName(name: String?) {
    if (!name.isNullOrBlank()) text = name
    isVisible = !name.isNullOrBlank()
}

@BindingAdapter("expenseAmount")
fun TextView.bindExpenseAmount(amount: Double?) {
    text = resources.getString(
        R.string.format_price,
        Currency.getInstance(Locale.getDefault()).symbol,
        amount ?: 0.0
    )
}

@BindingAdapter("valueAddedValue", "valueAddedIsPercentage")
fun TextView.bindValueAddedValue(value: Double, isPercentage: Boolean) {
    text = resources.getString(
        R.string.format_value_added,
        value,
        if (isPercentage) '%' else Currency.getInstance(Locale.getDefault()).symbol
    )
}