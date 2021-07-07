package me.polamokh.elcheck.data.local.valueadded

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "values_added")
data class ValueAdded(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "value_added_id") val valueAddedId: Int = 0,
    val isPercentage: Boolean,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Int
)
