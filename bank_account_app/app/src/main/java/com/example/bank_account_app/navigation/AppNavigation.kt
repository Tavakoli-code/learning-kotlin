package com.example.bank_account_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bank_account_app.screens.BankAccountScreen
import com.example.bank_account_app.screens.TransactionDetailScreen
import com.example.bank_account_app.screens.TransactionHistoryScreen
import com.example.bank_account_app.viewmodel.BankAccountViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    bankAccountViewModel: BankAccountViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val uiState by bankAccountViewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

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
                transactions = uiState.transactions,
                onBackClick = {
                    navController.popBackStack()
                },
                onTransactionClick = { transactionId ->
                    navController.navigate(
                        AppRoutes.transactionDetailRoute(transactionId)
                    )
                }
            )
        }
        composable(
            route = AppRoutes.TRANSACTION_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(AppRoutes.TRANSACTION_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments
                ?.getString(AppRoutes.TRANSACTION_ID)

            val transaction = transactionId?.let { id ->
                uiState.transactions.find { transaction ->
                    transaction.id == id
                }
            }

            TransactionDetailScreen(
                transaction = transaction,
                onBackClick = {
                    navController.popBackStack()
                },
                onDeleteClick = {
                    transaction?.id?.let { transactionId ->
                        coroutineScope.launch {
                            val result = bankAccountViewModel.deleteTransaction(
                                id = transactionId
                            )

                            if (result.success) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            )
        }
    }
}