package com.example.bank_account_app.data

import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction

class BankAccountRepository {
    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)
    private val transactions = mutableListOf<Transaction>()

    fun getAccount(): BankAccount {
        return account
    }

    fun getTransactions(): List<Transaction> {
        return transactions.toList()
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }
}