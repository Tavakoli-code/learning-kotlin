package com.example.bank_account_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val type: String,
    val amount: Double,
    val balanceAfter: Double,
    val createdAt: Long,
    val note: String?
)