package me.polamokh.elcheck.data.local.valueadded

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ValueAddedDao {

    @Insert
    suspend fun insertValuesAdded(vararg valuesAdded: ValueAdded)

    @Delete
    suspend fun deleteValuesAdded(vararg valuesAdded: ValueAdded)

    @Query("SELECT * FROM values_added WHERE order_id = :orderId")
    suspend fun getOrderValuesAddedByOrderId(orderId: Int): List<ValueAdded>

    @Query("SELECT * FROM values_added WHERE id = :valueAddedId")
    suspend fun getValueAddedById(valueAddedId: Int): ValueAdded?
}