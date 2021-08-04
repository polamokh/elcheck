package me.polamokh.elcheck.data.local.participant

import androidx.lifecycle.LiveData
import androidx.room.*
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpense
import me.polamokh.elcheck.data.local.participantexpense.ParticipantWithExpenses

@Dao
interface ParticipantDao {

    @Insert
    suspend fun insertParticipants(vararg participants: Participant): List<Long>

    @Insert
    suspend fun insertParticipantWithExpense(participantExpense: ParticipantExpense)

    @Delete
    suspend fun deleteParticipants(vararg participants: Participant)

    @Query("SELECT * FROM participants WHERE order_id = :orderId")
    fun getOrderParticipantsByOrderId(orderId: Long): LiveData<List<Participant>>

    @Query("SELECT * FROM participants WHERE order_id = :orderId")
    suspend fun getAsyncOrderParticipantsByOrderId(orderId: Long): List<Participant>

    @Query("SELECT * FROM participants WHERE participant_id = :participantId")
    suspend fun getParticipantById(participantId: Long): Participant?

    @Transaction
    @Query("SELECT * FROM participants WHERE participant_id = :participantId")
    suspend fun getParticipantByIdWithExpenses(participantId: Long): ParticipantWithExpenses?
}