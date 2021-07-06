package me.polamokh.elcheck.data.local.participantexpense

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.participant.Participant

data class ParticipantWithExpenses(
    @Embedded val participant: Participant,
    @Relation(
        parentColumn = "id",
        entityColumn = "participant_id",
        associateBy = Junction(ParticipantExpense::class)
    )
    val expenses: List<Expense>
)
