package com.example.bank_account_app.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val balanceAfter: Double,
    val createdAt: Long
) {}

enum class TransactionType {
    DEPOSIT,
    WITHDRAW
}

enum class TransactionFilter {
    ALL,
    DEPOSIT,
    WITHDRAW
}