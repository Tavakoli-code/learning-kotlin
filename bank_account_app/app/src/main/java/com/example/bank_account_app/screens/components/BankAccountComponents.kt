package com.example.bank_account_app.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionType

private fun formatAmount(amount: Double): String {
    return "%.2f AFN".format(amount)
}

@Composable
fun AccountHeader(owner: String, accountType: String, balance: String) {
    Text(text = "Owner: $owner")
    Text(text = "Type: $accountType")
    Text(text = "Balance: $balance")
}

@Composable
fun AmountInput(amountState: TextFieldState) {
    OutlinedTextField(
        state = amountState,
        label = { Text("Amount")},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ActionButtons(onWithdrawClick: () -> Unit, onDepositClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {

//            WITHDRAW BUTTON
        Button(
            onClick = onWithdrawClick,
            modifier = Modifier
                .width(110.dp)
        ) {
            Text(text = "Withdraw")
        }

//            DEPOSIT BUTTON
        Button(
            onClick = onDepositClick,
            modifier = Modifier
                .width(110.dp)
        ) {
            Text(text = "Deposit")
        }
    }
}

@Composable
fun ResultMessage(message: String) {
    Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TransactionHistory(transactions: List<Transaction>, modifier: Modifier = Modifier) {
    if (transactions.isEmpty()) {
        Text(
            text = "No transactions yet",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = when (transaction.type) {
                    TransactionType.DEPOSIT -> "Deposit"
                    TransactionType.WITHDRAW -> "Withdraw"
                },
                modifier = Modifier.weight(1F)
            )
            Text(
                text = formatAmount(transaction.amount),
                modifier = Modifier.weight(1F)
            )
            Text(
                text = formatAmount(transaction.balanceAfter),
                modifier = Modifier.weight(1F)
            )
        }
    }
}