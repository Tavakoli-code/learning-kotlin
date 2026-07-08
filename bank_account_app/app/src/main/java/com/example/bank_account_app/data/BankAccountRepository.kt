package com.example.bank_account_app.data

import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction

interface BankAccountRepository {
    fun getAccount(): BankAccount
    fun getTransactions(): List<Transaction>
    fun addTransaction(transaction: Transaction)
}

class InMemoryBankAccountRepository : BankAccountRepository {
    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)
    private val transactions = mutableListOf<Transaction>()

    override fun getAccount(): BankAccount {
        return account
    }

    override fun getTransactions(): List<Transaction> {
        return transactions.toList()
    }

    override fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }
}