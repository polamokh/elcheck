package me.polamokh.elcheck.core.expenses.add

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.core.model.ExpenseParticipant
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpense
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val expenseDao: ExpenseDao,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _expenseAmount = MutableLiveData(0.0)

    private val _expenseParticipants = MutableLiveData<List<ExpenseParticipant>>()
    val expenseParticipants: LiveData<List<ExpenseParticipant>>
        get() = _expenseParticipants

    private val _onNotifyItemChanged = MutableLiveData(-1)
    val onNotifyItemChanged: LiveData<Int>
        get() = _onNotifyItemChanged

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val participants = participantDao.getAsyncOrderParticipantsByOrderId(
                    state.get<Long>("orderId")!!
                )
                val expenseParticipantList = mutableListOf<ExpenseParticipant>()
                for (participant in participants) {
                    expenseParticipantList.add(ExpenseParticipant(participant))
                }
                _expenseParticipants.postValue(expenseParticipantList)
            }
        }
    }

    fun setExpenseAmount(value: Double) {
        _expenseAmount.value = value
    }

    fun recalculateParticipantExpenses(
        isChecked: Boolean,
        expenseParticipant: ExpenseParticipant
    ) {
        val currentList = expenseParticipants.value!!
        val expenseParticipantIndex = currentList.indexOf(expenseParticipant)
        currentList[expenseParticipantIndex].isChecked = isChecked
        if (!isChecked) {
            currentList[expenseParticipantIndex].value = 0.0
        }
        if (!isChecked)
            _onNotifyItemChanged.value = currentList.indexOf(expenseParticipant)

        recalculateParticipantExpenses()
    }

    fun recalculateParticipantExpenses() {
        val currentList = expenseParticipants.value!!
        val checkedParticipants = currentList.filter { it.isChecked }
        if (checkedParticipants.isNotEmpty()) {
            for (participant in checkedParticipants) {
                participant.value = _expenseAmount.value!! / checkedParticipants.size
            }
        }
        for (participant in checkedParticipants)
            _onNotifyItemChanged.value = currentList.indexOf(participant)
    }

    fun onNotifyItemChangedComplete() {
        _onNotifyItemChanged.value = -1
    }

    fun saveExpense() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentList = expenseParticipants.value!!
                val checkedParticipants = currentList.filter { it.isChecked }
                if (checkedParticipants.isNotEmpty()) {
                    val expenseIds = expenseDao.insertExpenses(
                        Expense(
                            name = "",
                            value = _expenseAmount.value!!,
                            orderId = state.get<Long>("orderId")!!
                        )
                    )
                    for (checkedParticipant in checkedParticipants) {
                        participantDao.insertParticipantWithExpense(
                            ParticipantExpense(
                                participantId = checkedParticipant.participant.participantId,
                                expenseId = expenseIds[0],
                                value = checkedParticipant.value
                            )
                        )
                    }
                }
            }
        }
    }
}