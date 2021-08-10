package me.polamokh.elcheck.data.local.participantexpense

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ParticipantExpenseDao {

    @Query("SELECT * FROM participant_expense WHERE participant_id = :participantId")
    suspend fun getParticipantByIdExpenses(participantId: Long): List<ParticipantExpense>

    @Query("SELECT SUM(value) FROM participant_expense WHERE participant_id = :participantId")
    suspend fun getParticipantByIdTotalExpenses(participantId: Long): Double?
}