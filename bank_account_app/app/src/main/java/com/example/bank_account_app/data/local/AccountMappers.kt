package com.example.bank_account_app.data.local

import com.example.bank_account_app.model.AccountType
import com.example.bank_account_app.model.BankAccount

fun AccountEntity.toDomain(): BankAccount {
    return BankAccount(
        accountOwner = owner,
        initialBalance = balance,
        accountType = AccountType.valueOf(accountType)
    )
}

fun BankAccount.toEntity(): AccountEntity {
    return AccountEntity(
        owner = accountOwner,
        accountType = accountType.name,
        balance = balance
    )
}