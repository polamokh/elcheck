package me.polamokh.elcheck.data.local.valueadded

import androidx.room.Embedded
import androidx.room.Relation
import me.polamokh.elcheck.data.local.order.Order

data class OrderWithValuesAdded(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "order_id"
    )
    val valueAdded: List<ValueAdded>
)
