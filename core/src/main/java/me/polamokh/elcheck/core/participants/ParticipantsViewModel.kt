package me.polamokh.elcheck.core.participants

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpenseDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantWithExpenses
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao
import javax.inject.Inject

@HiltViewModel
class ParticipantsViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val valueAddedDao: ValueAddedDao,
    private val participantExpenseDao: ParticipantExpenseDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val participants = participantDao.getParticipantsByOrderId(
        state.get<Long>("orderId")!!
    )

    val expenses = expenseDao.getExpensesByOrderId(
        state.get<Long>("orderId")!!
    )

    private val _participantExpenses = MutableLiveData<List<ParticipantWithExpenses>>()
    val participantExpenses: LiveData<List<ParticipantWithExpenses>>
        get() = _participantExpenses

    fun getParticipantExpenses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val participantExpenseList = mutableListOf<ParticipantWithExpenses>()
                for (participant in participants.value!!) {
                    val participantExpenses =
                        participantExpenseDao.getAsyncParticipantByIdExpenses(participant.participantId)
                    val expenses = mutableListOf<Expense>()
                    for (participantExpense in participantExpenses) {
                        val percentageValueAdded =
                            valueAddedDao.getTotalPercentageValuesAddedByOrderId(
                                state.get<Long>("orderId")!!
                            ) ?: 0.0
                        expenses.add(
                            Expense(
                                name = "",
                                value = participantExpense.value * (1 + percentageValueAdded / 100.0),
                                orderId = -1
                            )
                        )
                    }
                    participantExpenseList.add(ParticipantWithExpenses(participant, expenses))
                }
                _participantExpenses.postValue(participantExpenseList)
            }
        }
    }

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