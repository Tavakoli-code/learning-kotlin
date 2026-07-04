package com.example.bank_account_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bank_account_app.navigation.AppNavigation
import com.example.bank_account_app.ui.theme.Bank_account_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bank_account_appTheme {
                AppNavigation()
            }
        }
    }
}