package me.polamokh.elcheck.data.local.valueadded

import androidx.room.*
import me.polamokh.elcheck.data.local.order.Order

@Entity(
    tableName = "values_added",
    foreignKeys = [ForeignKey(
        entity = Order::class,
        parentColumns = ["order_id"],
        childColumns = ["order_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["order_id"])]
)
data class ValueAdded(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "value_added_id") val valueAddedId: Long = 0,
    val isPercentage: Boolean,
    val value: Double,
    @ColumnInfo(name = "order_id") val orderId: Long
)
