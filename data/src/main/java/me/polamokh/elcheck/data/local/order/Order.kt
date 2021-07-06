package me.polamokh.elcheck.data.local.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "order_time") val orderTime: String
)