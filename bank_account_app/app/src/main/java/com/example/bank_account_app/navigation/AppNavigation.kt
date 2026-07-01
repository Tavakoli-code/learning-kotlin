package com.example.bank_account_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bank_account_app.screens.BankAccountScreen
import com.example.bank_account_app.screens.TransactionHistoryScreen
import com.example.bank_account_app.viewmodel.BankAccountViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val bankAccountViewModel: BankAccountViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.BANK_ACCOUNT,
        modifier = modifier
    ) {
        composable(AppRoutes.BANK_ACCOUNT) {
            BankAccountScreen(
                viewModel = bankAccountViewModel,
                onViewHistoryClick = {
                    navController.navigate(AppRoutes.TRANSACTION_HISTORY)
                }
            )
        }
        composable(AppRoutes.TRANSACTION_HISTORY) {
            TransactionHistoryScreen(
                transactions = bankAccountViewModel.uiState.transactions,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}