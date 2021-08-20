package me.polamokh.elcheck.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val valueAddedDao: ValueAddedDao,
) : ViewModel() {

    private val _orderId = MutableLiveData<Long>()

    private val _orderTotalExpensesWithVA = MediatorLiveData<Double>()
    val orderTotalExpensesWithVA: LiveData<Double>
        get() = _orderTotalExpensesWithVA

    fun setOrderId(orderId: Long) {
        _orderId.value = orderId

        _orderTotalExpensesWithVA.apply {
            addSource(expenseDao.getExpensesByOrderId(_orderId.value!!)) {
                calculateTotalOrderExpensesWithVA(_orderId.value!!)
            }
            addSource(valueAddedDao.getValuesAddedByOrderId(_orderId.value!!)) {
                calculateTotalOrderExpensesWithVA(_orderId.value!!)
            }
        }
    }

    private fun calculateTotalOrderExpensesWithVA(orderId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var orderTotalExpensesWithVA = getTotalOrderExpenses(orderId)
                val orderTotalAmountVA = getTotalAmountVA(orderId)
                val orderTotalPercentageVA = getTotalPercentageVA(orderId)

                orderTotalExpensesWithVA = if (orderTotalExpensesWithVA > 0.0)
                    orderTotalExpensesWithVA *
                            (1 + (orderTotalAmountVA / orderTotalExpensesWithVA)
                                    + (orderTotalPercentageVA / 100.0))
                else
                    orderTotalExpensesWithVA *
                            (1 + (orderTotalPercentageVA / 100.0))

                _orderTotalExpensesWithVA.postValue(orderTotalExpensesWithVA)
            }
        }
    }

    private suspend fun getTotalOrderExpenses(orderId: Long) =
        expenseDao.getTotalExpensesByOrderId(orderId) ?: 0.0

    private suspend fun getTotalAmountVA(orderId: Long) =
        valueAddedDao.getTotalAmountValuesAddedByOrderId(orderId) ?: 0.0

    private suspend fun getTotalPercentageVA(orderId: Long) =
        valueAddedDao.getTotalPercentageValuesAddedByOrderId(orderId) ?: 0.0

    fun addParticipant(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                participantDao.insertParticipants(
                    Participant(name = name, orderId = _orderId.value!!)
                )
            }
        }
    }
}