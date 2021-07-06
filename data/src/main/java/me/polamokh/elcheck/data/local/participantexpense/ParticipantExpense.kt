package me.polamokh.elcheck.data.local.participantexpense

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "participant_expense", primaryKeys = ["participant_id", "expense_id"])
data class ParticipantExpense(
    @ColumnInfo(name = "participant_id") val participantId: Int,
    @ColumnInfo(name = "expense_id") val expenseId: Int,
    val value: Double
)
