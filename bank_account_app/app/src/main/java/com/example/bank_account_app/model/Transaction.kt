package com.example.bank_account_app.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val balanceAfter: Double,
    val createdAt: Long,
    val note: String?
)

enum class TransactionType {
    DEPOSIT,
    WITHDRAW
}

enum class TransactionFilter {
    ALL,
    DEPOSIT,
    WITHDRAW
}

enum class TransactionSort {
    NEWEST_FIRST,
    OLDEST_FIRST
}