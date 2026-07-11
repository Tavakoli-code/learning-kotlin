package com.example.bank_account_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bank_account_app.data.BankAccountRepository
import com.example.bank_account_app.data.InMemoryBankAccountRepository
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionResult
import com.example.bank_account_app.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankAccountViewModel(
    private val repository: BankAccountRepository = InMemoryBankAccountRepository()
): ViewModel() {

    private var account: BankAccount? = null

    private val _uiState = MutableStateFlow(
        BankAccountUiState(
            isLoading = true
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeAccount().collect { latestAccount ->
                account = latestAccount

                _uiState.update { currentState ->
                    currentState.copy(
                        owner = latestAccount.accountOwner,
                        accountType = latestAccount.accountTypeLabel,
                        balanceText = latestAccount.displayBalance,
                        isLoading = false
                    )
                }
            }
        }

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
        action: (BankAccount, Double) -> TransactionResult,
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

        val currentAccount = account
            ?: return BankAccountActionResult(
                success = false,
                message = "Account is still loading"
            )

        val now = System.currentTimeMillis()
        when (val result = action(currentAccount, amount)) {
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
                    repository.saveAccount(currentAccount)
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        balanceText = currentAccount.displayBalance
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
            action = { account, value -> account.deposit(value) },
            successMessage = "Deposit successful",
            transactionType = TransactionType.DEPOSIT,
            note = note
        )
    }

    fun withdraw(amount: Double?, note: String?): BankAccountActionResult {
        return handleTransaction(
            amount = amount,
            action = { account, value -> account.withdraw(value)},
            successMessage = "Withdraw successful",
            transactionType = TransactionType.WITHDRAW,
            note = note
        )
    }

    suspend fun resetData(): BankAccountActionResult {
        return try {
            repository.resetData()

            BankAccountActionResult(
                success = true,
                message = "Data reset successfully"
            )
        } catch (e: Exception) {
            BankAccountActionResult(
                success = false,
                message = "Failed to reset data"
            )
        }
    }

    suspend fun deleteTransaction(id: String): BankAccountActionResult {
        return try {
            repository.deleteTransaction(id)

            BankAccountActionResult(
                success = true,
                message = "Transaction deleted"
            )
        } catch (e: Exception) {
            BankAccountActionResult(
                success = false,
                message = "Failed to delete transaction"
            )
        }
    }
}

data class BankAccountActionResult(
    val success: Boolean,
    val message: String
)