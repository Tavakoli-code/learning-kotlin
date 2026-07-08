package com.example.bank_account_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String = "main_account",
    val owner: String,
    val accountType: String,
    val balance: Double
)