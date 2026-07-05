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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.viewmodel.BankAccountViewModel
import com.example.bank_account_app.screens.components.*
import com.example.bank_account_app.utils.formatAmount
import com.example.bank_account_app.viewmodel.BankAccountActionResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankAccountScreen(
    viewModel: BankAccountViewModel,
    onViewHistoryClick: () -> Unit,
) {
    val uiState = viewModel.uiState
    val amountState = rememberTextFieldState()
    val noteState = rememberTextFieldState()
    var amountError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var pendingWithdrawAmount by rememberSaveable {
        mutableStateOf<Double?>(null)
    }
    var pendingWithdrawNote by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun readValidAmount(): Double? {
        val amountText = amountState.text.toString().trim()
        val amount = amountText.toDoubleOrNull()

        if (amountText.isBlank()) {
            amountError = "Amount is required"
            return null
        }

        if (amount == null) {
            amountError = "Please enter a valid amount"
            return null
        }

        amountError = null
        return amount
    }

    fun finishTransaction(result: BankAccountActionResult) {
        if (result.success) {
            amountState.clearText()
            noteState.clearText()
        }

        focusManager.clearFocus()

        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = result.message
            )
        }
    }

    fun performTransaction(action: (Double?, String?) -> BankAccountActionResult) {
        val amount = readValidAmount()

        val note = noteState.text
            .toString()
            .trim()
            .takeIf { it.isNotBlank() }

        val result = action(amount, note)

        finishTransaction(result)
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

            AmountInput(
                amountState = amountState,
                errorMessage = amountError
            )

            Spacer(Modifier.height(8.dp))

            NoteInput(noteState = noteState)

            Spacer(Modifier.height(12.dp))

            ActionButtons(
                onWithdrawClick = {
                    val amount = readValidAmount()

                    if (amount != null) {
                        pendingWithdrawAmount = amount

                        pendingWithdrawNote = noteState.text
                            .toString()
                            .trim()
                            .takeIf { it.isNotBlank() }
                    }
                },
                onDepositClick = {
                    performTransaction(viewModel::deposit)
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

        if (pendingWithdrawAmount != null) {
            AlertDialog(
                onDismissRequest = {
                    pendingWithdrawAmount = null
                    pendingWithdrawNote = null
                },
                title = {
                    Text("Confirm Withdraw")
                },
                text = {
                    Text("Withdraw ${formatAmount((pendingWithdrawAmount!!))}?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val amount = pendingWithdrawAmount
                            val note = pendingWithdrawNote

                            if (amount != null) {
                                val result = viewModel.withdraw(amount, note)
                                pendingWithdrawAmount = null
                                pendingWithdrawNote = null
                                finishTransaction(result)
                            }
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            pendingWithdrawAmount = null
                            pendingWithdrawNote = null
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}