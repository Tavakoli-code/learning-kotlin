package com.example.bank_account_app.data

import com.example.bank_account_app.data.local.AccountDao
import com.example.bank_account_app.data.local.TransactionDao
import com.example.bank_account_app.data.local.toDomain
import com.example.bank_account_app.data.local.toEntity
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBankAccountRepository(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) : BankAccountRepository {

    private fun createDefaultAccount(): BankAccount {
        return BankAccount("Sajad Ali Tavakoli", 150.0)
    }

    override fun observeAccount(): Flow<BankAccount> {
        return accountDao.observeAccount()
            .map { accountEntity ->
                accountEntity?.toDomain() ?: createDefaultAccount()
            }
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeTransactions()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun saveAccount(account: BankAccount) {
        accountDao.saveAccount(account.toEntity())
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun resetData() {
        transactionDao.deleteAllTransactions()
        accountDao.deleteAccount()
    }
}