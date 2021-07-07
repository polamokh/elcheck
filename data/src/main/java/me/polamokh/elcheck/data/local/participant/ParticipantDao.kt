package me.polamokh.elcheck.data.local.participant

import androidx.lifecycle.LiveData
import androidx.room.*
import me.polamokh.elcheck.data.local.participantexpense.ParticipantWithExpenses

@Dao
interface ParticipantDao {

    @Insert
    suspend fun insertParticipants(vararg participants: Participant)

    @Delete
    suspend fun deleteParticipants(vararg participants: Participant)

    @Query("SELECT * FROM participants WHERE order_id = :orderId")
    fun getOrderParticipantsByOrderId(orderId: Int): LiveData<List<Participant>>

    @Query("SELECT * FROM participants WHERE participant_id = :participantId")
    suspend fun getParticipantById(participantId: Int): Participant?

    @Transaction
    @Query("SELECT * FROM participants WHERE participant_id = :participantId")
    suspend fun getParticipantByIdWithExpenses(participantId: Int): ParticipantWithExpenses?
}