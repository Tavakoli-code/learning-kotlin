package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.screens.components.AppTopBar
import com.example.bank_account_app.utils.displayName
import com.example.bank_account_app.utils.formatAmount
import com.example.bank_account_app.utils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transaction: Transaction?,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Transaction Detail",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            if (transaction == null) {
                Text(text = "Transaction detail not found")
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        TransactionDetailRow("ID", transaction.id)
                        TransactionDetailRow("Type", transaction.type.displayName())
                        TransactionDetailRow("Amount", formatAmount(transaction.amount))
                        TransactionDetailRow("Balance after", formatAmount(transaction.balanceAfter))
                        TransactionDetailRow("Date", formatDateTime(transaction.createdAt))
                        TransactionDetailRow("Note", value = transaction.note ?: "No note")

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = {
                                showDeleteDialog = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Delete Transaction")
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("Delete transaction?")
            },
            text = {
                Text("This transaction will be permanently removed.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TransactionDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = value,
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.End
        )
    }
}