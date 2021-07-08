package me.polamokh.elcheck.data.local.order

import androidx.lifecycle.LiveData
import androidx.room.*
import me.polamokh.elcheck.data.local.expense.OrderWithExpenses
import me.polamokh.elcheck.data.local.participant.OrderWithParticipants
import me.polamokh.elcheck.data.local.valueadded.OrderWithValuesAdded

@Dao
interface OrderDao {

    @Insert()
    suspend fun insertOrders(vararg orders: Order): List<Long>

    @Delete
    suspend fun deleteOrders(vararg orders: Order)

    @Query("SELECT * FROM orders WHERE order_id = :orderId")
    suspend fun getOrderById(orderId: Long): Order?

    @Query("SELECT * FROM orders ORDER BY order_time")
    fun getOrders(): LiveData<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :orderId")
    suspend fun getOrderByIdWithParticipants(orderId: Long): OrderWithParticipants?

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :orderId")
    suspend fun getOrderByIdWithExpenses(orderId: Long): OrderWithExpenses?

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :orderId")
    suspend fun getOrderByIdWithValuesAdded(orderId: Long): OrderWithValuesAdded?
}