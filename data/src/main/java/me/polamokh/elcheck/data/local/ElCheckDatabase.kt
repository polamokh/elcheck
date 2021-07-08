package me.polamokh.elcheck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.data.local.expense.ExpenseDao
import me.polamokh.elcheck.data.local.order.Order
import me.polamokh.elcheck.data.local.order.OrderDao
import me.polamokh.elcheck.data.local.participant.Participant
import me.polamokh.elcheck.data.local.participant.ParticipantDao
import me.polamokh.elcheck.data.local.participantexpense.ParticipantExpense
import me.polamokh.elcheck.data.local.valueadded.ValueAdded
import me.polamokh.elcheck.data.local.valueadded.ValueAddedDao

@Database(
    entities = [Order::class, Participant::class, Expense::class, ValueAdded::class, ParticipantExpense::class],
    version = 3,
    exportSchema = false
)
abstract class ElCheckDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    abstract fun participantDao(): ParticipantDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun valueAddedDao(): ValueAddedDao
}