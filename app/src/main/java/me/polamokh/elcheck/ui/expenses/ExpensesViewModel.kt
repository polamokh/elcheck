package me.polamokh.elcheck.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseDao: ExpenseDao
) : ViewModel() {

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                expenseDao.deleteExpenses(expense)
            }
        }
    }
}