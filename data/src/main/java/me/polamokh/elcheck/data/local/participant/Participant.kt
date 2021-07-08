package me.polamokh.elcheck.data.local.participant

import androidx.room.*
import me.polamokh.elcheck.data.local.order.Order

@Entity(
    tableName = "participants",
    foreignKeys = [ForeignKey(
        entity = Order::class,
        parentColumns = ["order_id"],
        childColumns = ["order_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["order_id"])]
)
data class Participant(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "participant_id") val participantId: Long = 0,
    val name: String,
    @ColumnInfo(name = "order_id") val orderId: Long
)
