package me.polamokh.elcheck.core.participants.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpense
import javax.inject.Inject

@HiltViewModel
class ParticipantsListViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val participants = participantDao.getOrderParticipantsByOrderId(
        state.get<Long>("orderId")!!
    )

    fun addParticipant() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val participantIds = participantDao.insertParticipants(
                    Participant(name = "Name", orderId = state.get<Long>("orderId")!!)
                )
                val expenseIds = expenseDao.insertExpenses(
                    Expense(name = "", value = 60.0, orderId = state.get<Long>("orderId")!!)
                )
                participantDao.insertParticipantWithExpense(
                    ParticipantExpense(participantIds[0], expenseIds[0], 60.0)
                )
            }
        }
    }
}