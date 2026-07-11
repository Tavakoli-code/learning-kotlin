package com.example.bank_account_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bank_account_app.navigation.AppNavigation
import com.example.bank_account_app.ui.theme.Bank_account_appTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bank_account_app.data.RoomBankAccountRepository
import com.example.bank_account_app.data.local.DatabaseProvider
import com.example.bank_account_app.viewmodel.BankAccountViewModel
import com.example.bank_account_app.viewmodel.BankAccountViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            val repository = remember {
                val database = DatabaseProvider.getDatabase(context)

                RoomBankAccountRepository(
                    transactionDao = database.transactionDao(),
                    accountDao = database.accountDao()
                )
            }

            val bankAccountViewModel: BankAccountViewModel = viewModel(
                factory = BankAccountViewModelFactory(repository)
            )

            Bank_account_appTheme {
                AppNavigation(
                    bankAccountViewModel = bankAccountViewModel
                )
            }
        }
    }
}