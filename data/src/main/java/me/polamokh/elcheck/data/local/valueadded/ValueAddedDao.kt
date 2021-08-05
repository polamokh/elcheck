package me.polamokh.elcheck.data.local.valueadded

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ValueAddedDao {

    @Insert
    suspend fun insertValuesAdded(vararg valuesAdded: ValueAdded): List<Long>

    @Delete
    suspend fun deleteValuesAdded(vararg valuesAdded: ValueAdded)

    @Query("SELECT * FROM values_added WHERE order_id = :orderId")
    fun getValuesAddedByOrderId(orderId: Long): LiveData<List<ValueAdded>>

    @Query("SELECT SUM(value) FROM values_added WHERE order_id = :orderId AND isPercentage = 1")
    suspend fun getTotalPercentageValuesAddedByOrderId(orderId: Long): Double?

    @Query("SELECT SUM(value) FROM values_added WHERE order_id = :orderId AND isPercentage = 0")
    suspend fun getTotalAmountValuesAddedByOrderId(orderId: Long): Double?

    @Query("SELECT * FROM values_added WHERE value_added_id = :valueAddedId")
    suspend fun getValueAddedById(valueAddedId: Long): ValueAdded?
}