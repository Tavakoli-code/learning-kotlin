package com.example.bank_account_app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionResult
import com.example.bank_account_app.model.TransactionType

class BankAccountViewModel: ViewModel() {
    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)

    var uiState by mutableStateOf(
        BankAccountUiState(
            owner = account.accountOwner,
            accountType = account.accountTypeLabel,
            balanceText = account.displayBalance
        )
    )
        private set

    private fun handleTransaction(
        amount: Double?,
        action: (Double) -> TransactionResult,
        successMessage: String,
        transactionType: TransactionType
    ): Boolean {
        if (amount == null) {
            uiState = uiState.copy(
                resultMessage = "Please enter a valid amount"
            )
            return false
        }
        when (val result = action(amount)) {
            is TransactionResult.Success -> {
                val newTransaction = Transaction(
                    type = transactionType,
                    amount = amount,
                    balanceAfter = result.newBalance
                )
                uiState = uiState.copy(
                    balanceText = account.displayBalance,
                    resultMessage = successMessage,
                    transactions =  uiState.transactions + newTransaction
                )
                return true
            }
            is TransactionResult.Failed -> {
                uiState = uiState.copy(
                    resultMessage = result.reason
                )
                return false
            }
        }
    }

    fun deposit(amount: Double?): Boolean {
        return handleTransaction(
            amount = amount,
            action = account::deposit,
            successMessage = "Deposit successful",
            transactionType = TransactionType.DEPOSIT
        )
    }

    fun withdraw(amount: Double?): Boolean {
        return handleTransaction(
            amount = amount,
            action = account::withdraw,
            successMessage = "Withdraw successful",
            transactionType = TransactionType.WITHDRAW
        )
    }
}