package me.polamokh.elcheck.data.local.participant

import androidx.room.Embedded
import androidx.room.Relation
import me.polamokh.elcheck.data.local.order.Order

data class OrderWithParticipants(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "order_id",
        entityColumn = "order_id",
    )
    val participants: List<Participant>
)