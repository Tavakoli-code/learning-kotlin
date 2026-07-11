package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.screens.components.AppTopBar
import com.example.bank_account_app.viewmodel.BankAccountActionResult
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    currentOwner: String,
    onBackClick: () -> Unit,
    onResetData: suspend () -> BankAccountActionResult,
    onUpdateOwner: suspend (String) -> BankAccountActionResult
) {
    var showResetDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var showEditOwnerDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var editedOwner by rememberSaveable(currentOwner) {
        mutableStateOf(currentOwner)
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    showResetDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Data")
            }

            OutlinedButton(
                onClick = {
                    editedOwner = currentOwner
                    showEditOwnerDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Change Account Owner")
            }
        }
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
                Text(
                    "This will delete all transactions and reset the account balance."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            val result = onResetData()

                            if (result.success) {
                                showResetDialog = false
                            }

                            snackbarHostState.showSnackbar(
                                message = result.message
                            )
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

    if (showEditOwnerDialog) {
        AlertDialog(
            onDismissRequest = {
                showEditOwnerDialog = false
            },
            title = {
                Text("Change account owner")
            },
            text = {
                OutlinedTextField(
                    value = editedOwner,
                    onValueChange = { newValue ->
                        editedOwner = newValue
                    },
                    label = {
                        Text("Account owner")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            val result = onUpdateOwner(editedOwner)

                            if (result.success) {
                                showEditOwnerDialog = false
                            }

                            snackbarHostState.showSnackbar(
                                message = result.message
                            )
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEditOwnerDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}