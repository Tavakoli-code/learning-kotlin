package com.example.bank_account_app.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.utils.displayName
import com.example.bank_account_app.utils.formatAmount
import com.example.bank_account_app.viewmodel.BankAccountUiState

@Composable
fun AccountHeader(owner: String, accountType: String, balance: String) {
    Text(text = "Owner: $owner")
    Text(text = "Type: $accountType")
    Text(text = "Balance: $balance")
}

@Composable
fun AmountInput(amountState: TextFieldState, errorMessage: String?) {
    OutlinedTextField(
        state = amountState,
        label = { Text("Amount")},
        lineLimits = TextFieldLineLimits.SingleLine,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text( text = errorMessage )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NoteInput(noteState: TextFieldState) {
    OutlinedTextField(
        state = noteState,
        label = { Text("Note") },
        lineLimits = TextFieldLineLimits.SingleLine,
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
fun TransactionHistory(
    transactions: List<Transaction>,
    emptyMessage: String,
    modifier: Modifier = Modifier,
    onTransactionClick: (String) -> Unit
) {
    if (transactions.isEmpty()) {
        Text(
            text = emptyMessage,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = {
                        onTransactionClick(transaction.id)
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable{
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = transaction.type.displayName(),
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

@Composable
fun BankAccountContent(
    uiState: BankAccountUiState,
    amountState: TextFieldState,
    noteState: TextFieldState,
    amountError: String?,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onViewHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        AccountHeader(
            owner = uiState.owner,
            accountType = uiState.accountType,
            balance = uiState.balanceText
        )

        Spacer(Modifier.height(15.dp))

        TransactionActionSection(
            amountState = amountState,
            noteState = noteState,
            amountError = amountError,
            onDepositClick = onDepositClick,
            onWithdrawClick = onWithdrawClick
        )

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = onViewHistoryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Transaction History")
        }

        Spacer(Modifier.height(50.dp))

        OutlinedButton(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }
    }
}

@Composable
private fun TransactionActionSection(
    amountState: TextFieldState,
    noteState: TextFieldState,
    amountError: String?,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit
) {
    AmountInput(
        amountState = amountState,
        errorMessage = amountError
    )

    Spacer(Modifier.height(8.dp))

    NoteInput(noteState = noteState)

    Spacer(Modifier.height(12.dp))

    ActionButtons(
        onWithdrawClick = onWithdrawClick,
        onDepositClick = onDepositClick
    )
}