package com.example.bank_account_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bank_account_app.navigation.AppNavigation
import com.example.bank_account_app.ui.theme.Bank_account_appTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bank_account_app.data.RoomBankAccountRepository
import com.example.bank_account_app.data.local.DatabaseProvider
import com.example.bank_account_app.viewmodel.BankAccountViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            val repository = remember {
                val database = DatabaseProvider.createDatabase(context)
                RoomBankAccountRepository(
                    transactionDao = database.transactionDao()
                )
            }

            val bankAccountViewModel: BankAccountViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return BankAccountViewModel(repository) as T
                    }
                }
            )

            Bank_account_appTheme {
                AppNavigation(
                    bankAccountViewModel = bankAccountViewModel
                )
            }
        }
    }
}