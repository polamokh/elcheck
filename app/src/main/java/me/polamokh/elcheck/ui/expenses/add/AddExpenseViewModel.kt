package me.polamokh.elcheck.ui.expenses.add

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpense
import me.polamokh.elcheck.model.ExpenseParticipant
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
                val participants = participantDao.getAsyncParticipantsByOrderId(
                    state.get<Long>("orderId")!!
                )
                val expenseParticipantList = participants.map { participant ->
                    ExpenseParticipant(participant)
                }
                _expenseParticipants.postValue(expenseParticipantList)
            }
        }
    }

    fun setExpenseAmount(value: Double) {
        _expenseAmount.value = value
        recalculateParticipantExpenses(expenseParticipants.value!!)
    }

    fun recalculateParticipantExpenses(
        isChecked: Boolean,
        expenseParticipant: ExpenseParticipant? = null
    ) {
        val currentList = expenseParticipants.value!!
        if (expenseParticipant != null) {
            val expenseParticipantIndex = currentList.indexOf(expenseParticipant)
            isParticipantChecked(currentList, expenseParticipantIndex, isChecked)
        } else {
            currentList.forEachIndexed { i, _ ->
                isParticipantChecked(currentList, i, isChecked)
            }
        }
        recalculateParticipantExpenses(currentList)
    }

    private fun isParticipantChecked(
        currentList: List<ExpenseParticipant>,
        i: Int,
        isChecked: Boolean
    ) {
        currentList[i].isChecked = isChecked
        if (!isChecked)
            notifyUncheckedParticipant(currentList, i)
    }

    private fun notifyUncheckedParticipant(currentList: List<ExpenseParticipant>, i: Int) {
        currentList[i].value = 0.0
        _onNotifyItemChanged.value = i
    }

    private fun recalculateParticipantExpenses(currentList: List<ExpenseParticipant>) {
        val checkedParticipants = currentList.filter { it.isChecked }
        if (checkedParticipants.isNotEmpty()) {
            val checkedParticipantsSize = checkedParticipants.size
            for (participant in checkedParticipants) {
                participant.value = _expenseAmount.value!! / checkedParticipantsSize
                _onNotifyItemChanged.value = currentList.indexOf(participant)
            }
        }
    }

    fun onNotifyItemChangedComplete() {
        _onNotifyItemChanged.value = -1
    }

    fun saveExpense(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentList = expenseParticipants.value!!
                val checkedParticipants = currentList.filter { it.isChecked }
                if (checkedParticipants.isNotEmpty()) {
                    val expenseIds = expenseDao.insertExpenses(
                        Expense(
                            name = name,
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