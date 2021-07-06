package me.polamokh.elcheck.data.local.order

import androidx.room.*
import me.polamokh.elcheck.data.local.expense.OrderWithExpenses
import me.polamokh.elcheck.data.local.participant.OrderWithParticipants
import me.polamokh.elcheck.data.local.valueadded.OrderWithValuesAdded

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrders(vararg orders: Order)

    @Delete
    suspend fun deleteOrders(vararg orders: Order)

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: Int): Order?

    @Query("SELECT * FROM orders ORDER BY order_time")
    suspend fun getOrders(): List<Order>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderByIdWithParticipants(orderId: Int): OrderWithParticipants?

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderByIdWithExpenses(orderId: Int): OrderWithExpenses?

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderByIdWithValuesAdded(orderId: Int): OrderWithValuesAdded?
}