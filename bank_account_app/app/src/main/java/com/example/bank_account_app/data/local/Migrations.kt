package com.example.bank_account_app.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE transactions
            ADD COLUMN updatedAt INTEGER
            """.trimIndent()
        )
    }
}