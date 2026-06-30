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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bank_account_app.R

val AppFontFamily = FontFamily(
    Font(R.font.open_sans, FontWeight.Normal)
)
@Composable
fun BankAccountScreen(modifier: Modifier = Modifier) {
    var resultMessage by remember {
        mutableStateOf("Result will appear here")
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

        Text(text = "Owner: Ali")
        Text(text = "Type: Savings")
        Text(text = "Balance: 100.00 AFN")

        Spacer(Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                state = rememberTextFieldState(),
                label = { Text("Withdraw amount")},
                modifier = Modifier.weight(1F)
            )
            Button(
                onClick = {
                    resultMessage = "Withdraw clicked"
                },
                modifier = Modifier
                    .width(110.dp)
            ) {
                Text(text = "Withdraw")
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                state = rememberTextFieldState(),
                label = { Text("Deposit amount") },
                modifier = Modifier.weight(1F)
            )
            Button(
                onClick = {
                    resultMessage = "Deposit clicked"
                },
                modifier = Modifier
                    .width(110.dp)
            ) {
                Text(text = "Deposit")
            }
        }

        Spacer(Modifier.height(32.dp))
        Text(
            text = resultMessage,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}