package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column(
        modifier = modifier.padding(16.dp).fillMaxSize()
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
        Text(text = "Account type: Savings")
        Text(text = "Balance: 100.00 AFN")

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(
                modifier = Modifier.weight(1F),
            ) {
                OutlinedTextField(
                    state = rememberTextFieldState(),
                    label = { Text("Withdraw amount")},
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        println("Withdraw clicked")
                    }
                ) {
                    Text(text = "Withdraw")
                }
            }
            Column(
                modifier = Modifier.weight(1F),
            ) {
                OutlinedTextField(
                    state = rememberTextFieldState(),
                    label = { Text("Deposit amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        println("Deposit clicked")
                    }
                ) {
                    Text(text = "Deposit")
                }

            }
        }
    }
}