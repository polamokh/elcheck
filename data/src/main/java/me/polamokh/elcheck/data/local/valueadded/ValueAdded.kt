package me.polamokh.elcheck.data.local.valueadded

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "values_added")
data class ValueAdded(
    @PrimaryKey val id: Int,
    val isPercentage: Boolean,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Int
)
