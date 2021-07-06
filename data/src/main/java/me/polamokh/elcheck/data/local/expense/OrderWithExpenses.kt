package me.polamokh.elcheck.data.local.expense

import androidx.room.Embedded
import androidx.room.Relation
import me.polamokh.elcheck.data.local.order.Order

data class OrderWithExpenses(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "order_id"
    )
    val expenses: List<Expense>
)
