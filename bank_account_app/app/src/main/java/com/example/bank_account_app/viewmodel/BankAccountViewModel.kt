package com.example.bank_account_app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionResult
import com.example.bank_account_app.model.TransactionType

class BankAccountViewModel: ViewModel() {
    private val account = BankAccount("Sajad Ali Tavakoli", 150.0)
    val owner: String
        get() = account.accountOwner
    val accountType: String
        get() = account.accountTypeLabel
    var balanceText by mutableStateOf(account.displayBalance)
        private set
    var resultMessage by mutableStateOf("Result will appear here")
        private set
    val transactions = mutableStateListOf<Transaction>()

    private fun handleTransaction(
        amount: Double?,
        action: (Double) -> TransactionResult,
        successMessage: String,
        transactionType: TransactionType
    ) {
        if (amount == null) {
            resultMessage = "Please enter a valid amount"
        } else {
            when (val result = action(amount)) {
                is TransactionResult.Success -> {
                    balanceText = account.displayBalance
                    resultMessage = successMessage
                    transactions.add(
                        Transaction(
                            type = transactionType,
                            amount = amount,
                            balanceAfter = result.newBalance
                        )
                    )
                }
                is TransactionResult.Failed -> {
                    resultMessage = result.reason
                }
            }
        }
    }

    fun deposit(amount: Double?) {
        handleTransaction(
            amount = amount,
            action = account::deposit,
            successMessage = "Deposit successful",
            transactionType = TransactionType.DEPOSIT
        )
    }

    fun withdraw(amount: Double?) {
        handleTransaction(
            amount = amount,
            action = account::withdraw,
            successMessage = "Withdraw successful",
            transactionType = TransactionType.WITHDRAW
        )
    }
}