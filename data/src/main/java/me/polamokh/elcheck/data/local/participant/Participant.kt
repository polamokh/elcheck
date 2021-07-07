package me.polamokh.elcheck.data.local.participant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "participant_id") val participantId: Int = 0,
    val name: String,
    @ColumnInfo(name = "order_id") val orderId: Int
)
