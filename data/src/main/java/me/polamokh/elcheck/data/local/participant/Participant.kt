package me.polamokh.elcheck.data.local.participant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "order_id") val orderId: Int
)
