package me.polamokh.elcheck.data.local.expense

import androidx.room.*
import me.polamokh.elcheck.data.local.order.Order

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = Order::class,
        parentColumns = ["order_id"],
        childColumns = ["order_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["order_id"])]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "expense_id") val expenseId: Long = 0,
    val name: String,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Long
)
