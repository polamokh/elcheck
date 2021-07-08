package me.polamokh.elcheck.data.local.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpenses(vararg expenses: Expense): List<Long>

    @Delete
    suspend fun deleteExpenses(vararg expenses: Expense)

    @Query("SELECT * FROM expenses WHERE order_id = :orderId")
    fun getOrderExpensesByOrderId(orderId: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE expense_id = :expenseId")
    suspend fun getExpenseById(expenseId: Long): Expense?
}