package me.polamokh.elcheck.core.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val expenses = expenseDao.getOrderExpensesByOrderId(
        state.get<Long>("orderId")!!
    )

    fun addExpense() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                expenseDao.insertExpenses(
                    Expense(name = "", value = 60.0, orderId = state.get<Long>("orderId")!!)
                )
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                expenseDao.deleteExpenses(expense)
            }
        }
    }
}