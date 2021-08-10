package me.polamokh.elcheck.ui.participants

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpenseDao
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao
import me.polamokh.elcheck.model.ParticipantWithTotalExpenses
import javax.inject.Inject

@HiltViewModel
class ParticipantsViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val valueAddedDao: ValueAddedDao,
    private val participantExpenseDao: ParticipantExpenseDao,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _participantWithTotalExpensesList =
        MediatorLiveData<List<ParticipantWithTotalExpenses>>()
    val participantWithTotalExpensesList: LiveData<List<ParticipantWithTotalExpenses>>
        get() = _participantWithTotalExpensesList

    init {
        _participantWithTotalExpensesList.apply {
            addSource(participantDao.getParticipantsByOrderId(state.get<Long>("orderId")!!)) {
                getParticipantExpenses()
            }
            addSource(expenseDao.getExpensesByOrderId(state.get<Long>("orderId")!!)) {
                getParticipantExpenses()
            }
            addSource(valueAddedDao.getValuesAddedByOrderId(state.get<Long>("orderId")!!)) {
                getParticipantExpenses()
            }
        }
    }

    private fun getParticipantExpenses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val participantExpenseList = mutableListOf<ParticipantWithTotalExpenses>()
                val participants =
                    participantDao.getAsyncParticipantsByOrderId(state.get<Long>("orderId")!!)
                for (participant in participants) {
                    participantExpenseList.add(
                        ParticipantWithTotalExpenses(
                            participant,
                            getTotalParticipantExpensesWithVA(participant.participantId)
                        )
                    )
                }
                _participantWithTotalExpensesList.postValue(participantExpenseList)
            }
        }
    }

    private suspend fun getTotalParticipantExpensesWithVA(participantId: Long): Double {
        val participantTotalExpenses = getTotalParticipantExpensesWithoutVA(participantId)
        val totalPercentageValueAdded = getTotalPercentageVA()
        val totalAmountValueAdded = getTotalAmountVA()
        val orderTotalExpenses = getTotalOrderExpenses()

        return if (orderTotalExpenses > 0.0)
            participantTotalExpenses *
                    (1 + (totalAmountValueAdded / orderTotalExpenses)
                            + (totalPercentageValueAdded / 100.0))
        else
            participantTotalExpenses *
                    (1 + (totalPercentageValueAdded / 100.0))
    }

    private suspend fun getTotalOrderExpenses() =
        expenseDao.getTotalExpensesByOrderId(state.get<Long>("orderId")!!) ?: 0.0

    private suspend fun getTotalAmountVA() =
        valueAddedDao.getTotalAmountValuesAddedByOrderId(state.get<Long>("orderId")!!) ?: 0.0

    private suspend fun getTotalPercentageVA() =
        valueAddedDao.getTotalPercentageValuesAddedByOrderId(state.get<Long>("orderId")!!) ?: 0.0

    private suspend fun getTotalParticipantExpensesWithoutVA(participantId: Long) =
        participantExpenseDao.getParticipantByIdTotalExpenses(participantId) ?: 0.0

    fun addParticipant() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                participantDao.insertParticipants(
                    Participant(name = "Name", orderId = state.get<Long>("orderId")!!)
                )
            }
        }
    }
}