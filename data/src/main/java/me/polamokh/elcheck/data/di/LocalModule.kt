package me.polamokh.elcheck.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.polamokh.elcheck.data.local.ElCheckDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    fun provideElCheckDatabase(@ApplicationContext context: Context): ElCheckDatabase {
        return Room.databaseBuilder(
            context,
            ElCheckDatabase::class.java,
            "elcheck.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderDao(elCheckDatabase: ElCheckDatabase) = elCheckDatabase.orderDao()

    @Singleton
    @Provides
    fun provideParticipantDao(elCheckDatabase: ElCheckDatabase) = elCheckDatabase.participantDao()

    @Singleton
    @Provides
    fun provideExpenseDao(elCheckDatabase: ElCheckDatabase) = elCheckDatabase.expenseDao()

    @Singleton
    @Provides
    fun provideValueAddedDao(elCheckDatabase: ElCheckDatabase) = elCheckDatabase.valueAddedDao()
}