package com.example.bank_account_app.viewmodel

import com.example.bank_account_app.model.AccountType
import com.example.bank_account_app.model.Transaction

data class BankAccountUiState(
    val owner: String = "",
    val accountType: String = "",
    val accountTypeValue: AccountType = AccountType.SAVING,
    val balanceText: String = "",
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false
)