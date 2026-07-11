package com.example.bank_account_app.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bank_account_database"
            )
                .addMigrations(MIGRATION_2_3)
                .build().also { createdDatabase ->
                database = createdDatabase
            }
        }
    }
}