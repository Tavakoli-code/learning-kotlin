package com.example.bank_account_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bank_account_app.data.BankAccountRepository

class BankAccountViewModelFactory(
    private val repository: BankAccountRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(BankAccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BankAccountViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}