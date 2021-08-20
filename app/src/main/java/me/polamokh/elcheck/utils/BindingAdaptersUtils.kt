package me.polamokh.elcheck.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import me.polamokh.elcheck.R
import java.util.*

@BindingAdapter("valueAddedIcon")
fun ImageView.bindValuedAddedIcon(isPercentage: Boolean?) {
    isPercentage?.let {
        setImageResource(if (isPercentage) R.drawable.ic_percentage else R.drawable.ic_dollar)
    }
}

@BindingAdapter("expenseName")
fun TextView.bindExpenseName(name: String?) {
    if (!name.isNullOrBlank()) text = name
    isVisible = !name.isNullOrBlank()
}

@BindingAdapter("expenseAmount")
fun TextView.bindExpenseAmount(amount: Double?) {
    text = resources.getString(
        R.string.price_format,
        Currency.getInstance(Locale.getDefault()).symbol,
        amount ?: 0.0
    )
}