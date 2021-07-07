package me.polamokh.elcheck.data.local.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "expense_id") val expenseId: Int = 0,
    val name: String,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Int
)
