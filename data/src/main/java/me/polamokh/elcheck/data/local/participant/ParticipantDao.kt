package me.polamokh.elcheck.data.local.participant

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.polamokh.elcheck.data.local.participantexpense.ParticipantWithExpenses

@Dao
interface ParticipantDao {

    @Insert
    suspend fun insertParticipants(vararg participants: Participant)

    @Delete
    suspend fun deleteParticipants(vararg participants: Participant)

    @Query("SELECT * FROM participants WHERE order_id = :orderId")
    suspend fun getOrderParticipantsByOrderId(orderId: Int): List<Participant>

    @Query("SELECT * FROM participants WHERE id = :participantId")
    suspend fun getParticipantById(participantId: Int): Participant?

    @Query("SELECT * FROM participants WHERE id = :participantId")
    suspend fun getParticipantByIdWithExpenses(participantId: Int): ParticipantWithExpenses?
}