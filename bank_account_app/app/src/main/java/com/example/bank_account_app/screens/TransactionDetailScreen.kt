package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transaction: Transaction?,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Transaction Detail",
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFontFamily,
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBackClick,
                    ) {
                        Text(
                            "Back",
                        )
                    }
                }
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
                Text(text = "Transaction type: ${transaction.type}")
                Text(text = "Transaction amount: ${"%.2f AFN".format(transaction.amount)}")
                Text(text = "Balance after: ${transaction.balanceAfter}")
            }
        }
    }
}