package me.polamokh.elcheck.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.valueadded.ValueAdded
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val valueAddedDao: ValueAddedDao,
) : ViewModel() {

    private val _orderId = MutableLiveData<Long>()

    val expenses: LiveData<List<Expense>> = Transformations.switchMap(_orderId) { orderId ->
        expenseDao.getExpensesByOrderId(orderId)
    }

    val valuesAdded: LiveData<List<ValueAdded>> = Transformations.switchMap(_orderId) { orderId ->
        valueAddedDao.getValuesAddedByOrderId(orderId)
    }

    fun setOrderId(orderId: Long) {
        _orderId.value = orderId
    }
}