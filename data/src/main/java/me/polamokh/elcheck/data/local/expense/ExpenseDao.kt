package me.polamokh.elcheck.data.local.expense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpenses(vararg expenses: Expense)

    @Delete
    suspend fun deleteExpenses(vararg expenses: Expense)

    @Query("SELECT * FROM expenses WHERE order_id = :orderId")
    suspend fun getOrderExpensesByOrderId(orderId: Int): List<Expense>

    @Query("SELECT * FROM expenses WHERE expense_id = :expenseId")
    suspend fun getExpenseById(expenseId: Int): Expense?
}