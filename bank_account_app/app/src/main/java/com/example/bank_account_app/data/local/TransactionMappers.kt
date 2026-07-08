package com.example.bank_account_app.data.local

import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionType

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        type = TransactionType.valueOf(type),
        amount = amount,
        balanceAfter = balanceAfter,
        createdAt = createdAt,
        note = note
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        type = type.name,
        amount = amount,
        balanceAfter = balanceAfter,
        createdAt = createdAt,
        note = note
    )
}