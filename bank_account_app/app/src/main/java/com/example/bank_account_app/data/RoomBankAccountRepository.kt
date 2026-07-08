package com.example.bank_account_app.data

import com.example.bank_account_app.data.local.TransactionDao
import com.example.bank_account_app.data.local.toDomain
import com.example.bank_account_app.data.local.toEntity
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBankAccountRepository(
    private val transactionDao: TransactionDao
) : BankAccountRepository {

    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)

    override fun getAccount(): BankAccount {
        return account
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeTransactions()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }
}