package com.example.bank_account_app

import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.TransactionResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BankAccountTest {

    @Test
    fun deposit_withPositiveAmount_increasesBalance() {
//        Arrange
        val account = BankAccount("Test User", 100.0)

//        Act
        val result = account.deposit(50.0)

//        Assert
        assertTrue(result is TransactionResult.Success)
        assertEquals(150.0, account.balance, 0.001)

    }

    @Test
    fun deposit_withNegativeAmount_returnsFailedAndKeepsBalance() {
        val account = BankAccount("Test User", 100.0)

        val result = account.deposit(-50.0)

        assertTrue(result is TransactionResult.Failed)
        if (result is TransactionResult.Failed) {
            assertEquals("Deposit amount must be positive", result.reason)
        }
        assertEquals(100.0, account.balance, 0.001)
    }
}