package com.example.bank_account_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bank_account_app.data.BankAccountRepository
import com.example.bank_account_app.data.InMemoryBankAccountRepository
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionResult
import com.example.bank_account_app.model.TransactionType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class BankAccountViewModel(
    private val repository: BankAccountRepository = InMemoryBankAccountRepository()
): ViewModel() {
    private val account = repository.getAccount()

    private val _uiState = MutableStateFlow(
        BankAccountUiState(
            owner = account.accountOwner,
            accountType = account.accountTypeLabel,
            balanceText = account.displayBalance
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeTransactions().collect { transactions ->
                _uiState.update { currentState ->
                    currentState.copy(transactions = transactions)
                }
            }
        }
    }

    private fun handleTransaction(
        amount: Double?,
        action: (Double) -> TransactionResult,
        successMessage: String,
        transactionType: TransactionType,
        note: String?
    ): BankAccountActionResult {
        if (amount == null) {
            return BankAccountActionResult(
                success = false,
                message = "Please enter a valid amount"
            )
        }
        val now = System.currentTimeMillis()
        when (val result = action(amount)) {
            is TransactionResult.Success -> {
                val newTransaction = Transaction(
                    id = now.toString(),
                    type = transactionType,
                    amount = amount,
                    balanceAfter = result.newBalance,
                    createdAt = now,
                    note = note
                )

                viewModelScope.launch {
                    repository.addTransaction(newTransaction)
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        balanceText = account.displayBalance
                    )
                }
                return BankAccountActionResult(
                    success = true,
                    message = successMessage
                )
            }
            is TransactionResult.Failed -> {
                return BankAccountActionResult(
                    success = false,
                    message = result.reason
                )
            }
        }
    }

    fun deposit(amount: Double?, note: String?): BankAccountActionResult {
        return handleTransaction(
            amount = amount,
            action = account::deposit,
            successMessage = "Deposit successful",
            transactionType = TransactionType.DEPOSIT,
            note = note
        )
    }

    fun withdraw(amount: Double?, note: String?): BankAccountActionResult {
        return handleTransaction(
            amount = amount,
            action = account::withdraw,
            successMessage = "Withdraw successful",
            transactionType = TransactionType.WITHDRAW,
            note = note
        )
    }

    fun loadAccount() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            delay(1000.milliseconds)

            _uiState.update { currentState ->
                currentState.copy(
                    owner = account.accountOwner,
                    accountType = account.accountTypeLabel,
                    balanceText = account.displayBalance,
                    isLoading = false
                )
            }
        }
    }
}

data class BankAccountActionResult(
    val success: Boolean,
    val message: String
)