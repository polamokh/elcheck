package me.polamokh.elcheck.data.local.participantexpense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.participant.Participant

@Entity(
    tableName = "participant_expense",
    primaryKeys = ["participant_id", "expense_id"],
    foreignKeys = [ForeignKey(
        entity = Participant::class,
        parentColumns = ["participant_id"],
        childColumns = ["participant_id"],
        onDelete = ForeignKey.RESTRICT
    ), ForeignKey(
        entity = Expense::class,
        parentColumns = ["expense_id"],
        childColumns = ["expense_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["expense_id"])]
)
data class ParticipantExpense(
    @ColumnInfo(name = "participant_id") val participantId: Long,
    @ColumnInfo(name = "expense_id") val expenseId: Long,
    val value: Double
)
