package com.example.bank_account_app.data

import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface BankAccountRepository {
    fun observeAccount(): Flow<BankAccount>
    fun observeTransactions(): Flow<List<Transaction>>
    suspend fun saveAccount(account: BankAccount)
    suspend fun addTransaction(transaction: Transaction)
    suspend fun resetData()
}

class InMemoryBankAccountRepository : BankAccountRepository {
    private val account = MutableStateFlow(
        BankAccount("Sajad Ali Tavakoli", 150.0)
    )
    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())

    override fun observeAccount(): Flow<BankAccount> {
        return this.account.asStateFlow()
    }

    override suspend fun saveAccount(account: BankAccount) {
        this.account.value = account
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactions.asStateFlow()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactions.value += transaction
    }

    override suspend fun resetData() {
        transactions.value = emptyList()
        account.value = BankAccount("Sajad Ali Tavakoli", 150.0)
    }
}