package me.polamokh.elcheck.model

import me.polamokh.elcheck.data.local.participant.Participant

data class ParticipantWithTotalExpenses(
    val participant: Participant,
    val totalExpenses: Double = 0.0
)
