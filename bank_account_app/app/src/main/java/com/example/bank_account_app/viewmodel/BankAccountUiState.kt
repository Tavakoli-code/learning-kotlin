package com.example.bank_account_app.viewmodel

import com.example.bank_account_app.model.Transaction

data class BankAccountUiState(
    val owner: String = "",
    val accountType: String = "",
    val balanceText: String = "",
    val transactions: List<Transaction> = emptyList()
)