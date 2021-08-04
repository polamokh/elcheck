package me.polamokh.elcheck.core.model

import me.polamokh.elcheck.data.local.participant.Participant

data class ExpenseParticipant(
    val participant: Participant,
    var isChecked: Boolean = false,
    var value: Double = 0.0
)
