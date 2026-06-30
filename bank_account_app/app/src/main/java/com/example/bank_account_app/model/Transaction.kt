package com.example.bank_account_app.model

data class Transaction(
    val type: TransactionType,
    val amount: Double,
    val balanceAfter: Double
) {}

enum class TransactionType {
    DEPOSIT,
    WITHDRAW
}