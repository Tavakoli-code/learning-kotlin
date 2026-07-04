package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.R
import com.example.bank_account_app.viewmodel.BankAccountViewModel
import com.example.bank_account_app.screens.components.*

val AppFontFamily = FontFamily(
    Font(R.font.open_sans, FontWeight.Normal)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankAccountScreen(
    viewModel: BankAccountViewModel,
    onViewHistoryClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Bank Account"
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.uiState
        val amountState = rememberTextFieldState()

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
                    val success = viewModel.withdraw(amount)
                    if (success) { amountState.clearText() }
                },
                onDepositClick = {
                    val amount = amountState.text.toString().toDoubleOrNull()
                    val success = viewModel.deposit(amount)
                    if (success) { amountState.clearText() }
                }
            )

            Spacer(Modifier.height(32.dp))
            ResultMessage(message = uiState.resultMessage)

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