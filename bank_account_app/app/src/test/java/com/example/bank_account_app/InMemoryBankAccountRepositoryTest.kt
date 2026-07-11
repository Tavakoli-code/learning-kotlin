package com.example.bank_account_app.data

import com.example.bank_account_app.model.AccountType
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class InMemoryBankAccountRepositoryTest {

    @Test
    fun addTransaction_emitsUpdatedTransactionList() = runBlocking {
        val repository = InMemoryBankAccountRepository()

        val transaction = Transaction(
            id = "test-id",
            type = TransactionType.DEPOSIT,
            amount = 100.0,
            balanceAfter = 250.0,
            createdAt = 1L,
            note = "Test deposit"
        )

        repository.addTransaction(transaction)

        val transactions = repository
            .observeTransactions()
            .first()

        assertEquals(1, transactions.size)
        assertEquals(transaction, transactions.first())
    }

    @Test
    fun resetData_clearsAllTransactions() = runBlocking {
        val repository = InMemoryBankAccountRepository()

        val firstTransaction = Transaction(
            id = "transaction-1",
            type = TransactionType.DEPOSIT,
            amount = 100.0,
            balanceAfter = 250.0,
            createdAt = 1L,
            note = "First transaction"
        )

        val secondTransaction = Transaction(
            id = "transaction-2",
            type = TransactionType.WITHDRAW,
            amount = 50.0,
            balanceAfter = 200.0,
            createdAt = 2L,
            note = "Second transaction"
        )

        repository.addTransaction(firstTransaction)
        repository.addTransaction(secondTransaction)

        repository.resetData()

        val transactions = repository
            .observeTransactions()
            .first()

        assertEquals(0, transactions.size)
    }

    @Test
    fun saveAccount_emitsUpdatedAccount() = runBlocking {
        val repository = InMemoryBankAccountRepository()

        val updatedAccount = BankAccount(
            accountOwner = "Test User",
            initialBalance = 500.0,
            accountType = AccountType.BUSINESS
        )

        repository.saveAccount(updatedAccount)

        val account = repository
            .observeAccount()
            .first()

        assertEquals("Test User", account.accountOwner)
        assertEquals(500.0, account.balance, 0.001)
        assertEquals(AccountType.BUSINESS, account.accountType)
    }
}