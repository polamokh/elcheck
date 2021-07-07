package me.polamokh.elcheck.core.participants.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import javax.inject.Inject

@HiltViewModel
class ParticipantsListViewModel @Inject constructor(
    private val participantDao: ParticipantDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val participants = participantDao.getOrderParticipantsByOrderId(
        state.get<Int>("orderId")!!
    )

    fun addParticipant() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                participantDao.insertParticipants(
                    Participant(name = "Name", orderId = state.get<Int>("orderId")!!)
                )
            }
        }
    }
}