package com.example.bank_account_app.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun createDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "bank_account_database"
        ).build()
    }
}