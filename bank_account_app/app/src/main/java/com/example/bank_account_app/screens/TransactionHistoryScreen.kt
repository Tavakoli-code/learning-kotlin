package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.model.TransactionFilter
import com.example.bank_account_app.model.TransactionSort
import com.example.bank_account_app.model.TransactionType
import com.example.bank_account_app.screens.components.formatAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    transactions: List<Transaction>,
    onBackClick: () -> Unit,
    onTransactionClick: (String) -> Unit
) {
    var selectedFilter by rememberSaveable {
        mutableStateOf(TransactionFilter.ALL)
    }

    var selectedSort by rememberSaveable {
        mutableStateOf(TransactionSort.NEWEST_FIRST)
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

    val visibleTransactions = when (selectedSort) {
        TransactionSort.NEWEST_FIRST -> filteredTransactions.sortedByDescending { transaction ->
            transaction.createdAt
        }

        TransactionSort.OLDEST_FIRST -> filteredTransactions.sortedBy { transaction ->
            transaction.createdAt
        }
    }

    val totalTransactions = transactions.count()

    val totalDeposits = transactions
        .filter { transaction ->
            transaction.type == TransactionType.DEPOSIT
        }
        .sumOf { transaction ->
            transaction.amount
        }

    val totalWithdrawals = transactions
        .filter { transaction ->
            transaction.type == TransactionType.WITHDRAW
        }
        .sumOf { transaction ->
            transaction.amount
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
            TransactionSummaryCard(
                totalTransactions = totalTransactions,
                totalDeposits = totalDeposits,
                totalWithdrawals = totalWithdrawals
            )
            Spacer(Modifier.height(12.dp))

            TransactionFilterRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { filter ->
                    selectedFilter = filter
                }
            )

            TransactionSortButton(
                selectedSort = selectedSort,
                onSortChange = { sort ->
                    selectedSort = sort
                }
            )
            Spacer(Modifier.height(8.dp))

            TransactionHistory(
                transactions = visibleTransactions,
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

@Composable
private fun TransactionSummaryCard(
    totalTransactions: Int,
    totalDeposits: Double,
    totalWithdrawals: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SummaryRow("Total Transactions", totalTransactions.toString() )
            SummaryRow("Total Deposits", formatAmount(totalDeposits) )
            SummaryRow("Total Withdrawals", formatAmount(totalWithdrawals) )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun TransactionFilterRow(
    selectedFilter: TransactionFilter,
    onFilterSelected: (TransactionFilter) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TransactionFilterChip(
            text = "All",
            selected = selectedFilter == TransactionFilter.ALL,
            onClick = { onFilterSelected(TransactionFilter.ALL) }
        )
        TransactionFilterChip(
            text = "Deposits",
            selected = selectedFilter == TransactionFilter.DEPOSIT,
            onClick = { onFilterSelected(TransactionFilter.DEPOSIT) }
        )
        TransactionFilterChip(
            text = "Withdrawals",
            selected = selectedFilter == TransactionFilter.WITHDRAW,
            onClick = { onFilterSelected(TransactionFilter.WITHDRAW) }
        )
    }
}

@Composable
private fun TransactionSortButton(
    selectedSort: TransactionSort,
    onSortChange: (TransactionSort) -> Unit
) {
    TextButton(
        onClick = {
            val newSort = when (selectedSort) {
                TransactionSort.NEWEST_FIRST -> TransactionSort.OLDEST_FIRST
                TransactionSort.OLDEST_FIRST -> TransactionSort.NEWEST_FIRST
            }

            onSortChange(newSort)
        }
    ) {
        Text(
            text = when (selectedSort) {
                TransactionSort.NEWEST_FIRST -> "Sort: Newest first"
                TransactionSort.OLDEST_FIRST -> "Sort: Oldest first"
            }
        )
    }
}