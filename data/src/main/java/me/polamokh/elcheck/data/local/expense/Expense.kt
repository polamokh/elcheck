package me.polamokh.elcheck.data.local.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey val id: Int,
    val name: String,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Int
)
