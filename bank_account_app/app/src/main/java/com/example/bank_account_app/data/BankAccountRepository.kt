package com.example.bank_account_app.data

import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface BankAccountRepository {
    fun getAccount(): BankAccount
    fun observeTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
}

class InMemoryBankAccountRepository : BankAccountRepository {
    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)
    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())

    override fun getAccount(): BankAccount {
        return account
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactions.asStateFlow()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactions.value += transaction
    }
}