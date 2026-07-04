package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.viewmodel.BankAccountViewModel
import com.example.bank_account_app.screens.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankAccountScreen(
    viewModel: BankAccountViewModel,
    onViewHistoryClick: () -> Unit,
) {
    val uiState = viewModel.uiState
    val amountState = rememberTextFieldState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    fun finishTransaction(result: BankAccountViewModel.BankAccountActionResult) {
        if (result.success) {
            amountState.clearText()
        }

        focusManager.clearFocus()

        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = result.message
            )
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Bank Account"
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
                )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            AccountHeader(
                owner = uiState.owner,
                accountType = uiState.accountType,
                balance = uiState.balanceText
            )

            Spacer(Modifier.height(15.dp))

            AmountInput(amountState = amountState)

            Spacer(Modifier.height(12.dp))

            ActionButtons(
                onWithdrawClick = {
                    val amount = amountState.text.toString().toDoubleOrNull()
                    val result = viewModel.withdraw(amount)
                    finishTransaction(result)
                },
                onDepositClick = {
                    val amount = amountState.text.toString().toDoubleOrNull()
                    val result = viewModel.deposit(amount)
                    finishTransaction(result)
                }
            )

            Spacer(Modifier.height(50.dp))
            Button(
                onClick = onViewHistoryClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text( text = "View Transaction History")
            }
        }
    }
}