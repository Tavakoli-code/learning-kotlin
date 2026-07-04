package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.screens.components.TransactionHistory
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.model.TransactionFilter
import com.example.bank_account_app.model.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    transactions: List<Transaction>,
    onBackClick: () -> Unit,
    onTransactionClick: (String) -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf(TransactionFilter.ALL)
    }
    val filteredTransactions = when (selectedFilter) {
        TransactionFilter.ALL -> transactions

        TransactionFilter.DEPOSIT -> transactions.filter { transaction ->
            transaction.type == TransactionType.DEPOSIT
        }

        TransactionFilter.WITHDRAW -> transactions.filter { transaction ->
            transaction.type == TransactionType.WITHDRAW
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Transaction History",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransactionFilterChip(
                    text = "All",
                    selected = selectedFilter == TransactionFilter.ALL,
                    onClick = { selectedFilter = TransactionFilter.ALL }
                )
                TransactionFilterChip(
                    text = "Deposit",
                    selected = selectedFilter == TransactionFilter.DEPOSIT,
                    onClick = { selectedFilter = TransactionFilter.DEPOSIT }
                )
                TransactionFilterChip(
                    text = "Withdraw",
                    selected = selectedFilter == TransactionFilter.WITHDRAW,
                    onClick = { selectedFilter = TransactionFilter.WITHDRAW }
                )
            }
            Spacer(Modifier.height(8.dp))
            TransactionHistory(
                transactions = filteredTransactions,
                modifier = Modifier.weight(1f),
                onTransactionClick = onTransactionClick
            )
        }
    }
}

@Composable
private fun TransactionFilterChip (
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(text)
        }
    )
}