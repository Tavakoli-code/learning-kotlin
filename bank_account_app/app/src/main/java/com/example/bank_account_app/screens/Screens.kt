package com.example.bank_account_app.screens

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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bank_account_app.R
import com.example.bank_account_app.model.BankAccount
import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionResult
import com.example.bank_account_app.model.TransactionType

val AppFontFamily = FontFamily(
    Font(R.font.open_sans, FontWeight.Normal)
)

fun formatAmount(amount: Double): String {
    return "%.2f AFN".format(amount)
}
@Composable
fun BankAccountScreen(modifier: Modifier = Modifier) {
    var resultMessage by remember {
        mutableStateOf("Result will appear here")
    }
    val account = remember {
        BankAccount("Sajad Ali Tavakoli", 150.0)
    }
    val transactions = remember {
        mutableStateListOf<Transaction>()
    }
    var balanceText by remember {
        mutableStateOf(account.displayBalance)
    }
    val amountState = rememberTextFieldState()

    fun handleTransaction(
        action: (Double) -> TransactionResult,
        successMessage: String,
        transactionType: TransactionType
    ) {
        val amount = amountState.text.toString().toDoubleOrNull()

        if (amount == null) {
            resultMessage = "Please enter a valid amount"
            return
        } else {
            when (val result = action(amount)) {
                is TransactionResult.Success -> {
                    balanceText = account.displayBalance
                    resultMessage = successMessage
                    amountState.clearText()
                    transactions.add(
                        Transaction(
                            type = transactionType,
                            amount = amount,
                            balanceAfter = result.newBalance
                        )
                    )
                }
                is TransactionResult.Failed -> {
                    resultMessage = result.reason
                }
            }
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Bank Account",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontFamily = AppFontFamily,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        AccountHeader(
            owner = account.accountOwner,
            accountType = account.accountTypeLabel,
            balance = balanceText
        )

        Spacer(Modifier.height(15.dp))

        AmountInput(amountState = amountState)

        Spacer(Modifier.height(12.dp))

        ActionButtons(
            onWithdrawClick = {
                handleTransaction(
                    action = account::withdraw,
                    successMessage = "Withdraw successful",
                    transactionType = TransactionType.DEPOSIT
                )
            },
            onDepositClick = {
                handleTransaction(
                    action = account::deposit,
                    successMessage = "Deposit successful",
                    transactionType = TransactionType.DEPOSIT
                )
            }
        )

        Spacer(Modifier.height(32.dp))
        Text(
            text = resultMessage,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(50.dp))
        Text(
            text = "Transaction History",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
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
                modifier = Modifier
                    .weight(1F)
                    .padding(top = 10.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
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
fun ActionButtons(
    onWithdrawClick: () -> Unit,
    onDepositClick: () -> Unit
) {
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