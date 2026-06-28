package com.example.bank_account_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BankAccountScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Bank Account")
        Text(text = "Owner: Ali")
        Text(text = "Balance: 100.00 AFN")

        Button(
            onClick = {
                println("Deposit clicked")
            }
        ) {
            Text(text = "Deposit")
        }
    }
}