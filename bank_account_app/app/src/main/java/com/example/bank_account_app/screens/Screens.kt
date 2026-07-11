package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    onSettingsClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    var showResetDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun readNote(): String? {
        return noteState.text
            .toString()
            .trim()
            .takeIf { it.isNotBlank() }
    }

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
        val amount = readValidAmount() ?: return

        val note = readNote()

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
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            BankAccountContent(
                uiState = uiState,
                amountState = amountState,
                noteState = noteState,
                amountError = amountError,
                onDepositClick = {
                    performTransaction(viewModel::deposit)
                },
                onWithdrawClick = {
                    val amount = readValidAmount()

                    if (amount != null) {
                        pendingWithdrawAmount = amount
                        pendingWithdrawNote = readNote()

                        focusManager.clearFocus()
                    }
                },
                onViewHistoryClick = onViewHistoryClick,
                onSettingsClick = onSettingsClick,
                onResetClick = {
                    showResetDialog = true
                },
                modifier = Modifier.padding(innerPadding)
            )
        }

        pendingWithdrawAmount?.let { amount ->
            AlertDialog(
                onDismissRequest = {
                    pendingWithdrawAmount = null
                    pendingWithdrawNote = null
                },
                title = {
                    Text("Confirm Withdraw")
                },
                text = {
                    Text("Withdraw ${formatAmount(amount)}?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val result = viewModel.withdraw(amount, pendingWithdrawNote)

                            pendingWithdrawAmount = null
                            pendingWithdrawNote = null

                            finishTransaction(result)
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

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = {
                    showResetDialog = false
                },
                title = {
                    Text("Reset data?")
                },
                text = {
                    Text("This will delete all transactions and reset your balance.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showResetDialog = false

                            coroutineScope.launch {
                                val result = viewModel.resetData()
                                finishTransaction(result)
                            }
                        }
                    ) {
                        Text("Reset")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showResetDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}