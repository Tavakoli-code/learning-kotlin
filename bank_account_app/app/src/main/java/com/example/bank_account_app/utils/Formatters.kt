package com.example.bank_account_app.utils

import com.example.bank_account_app.model.Transaction
import com.example.bank_account_app.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatAmount(amount: Double): String {
    return "%.2f AFN".format(amount)
}

fun formatDateTime(timestamp: Long): String {
    val formatter = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    )

    return formatter.format(Date(timestamp))
}

fun TransactionType.displayName(): String {
    return when (this) {
        TransactionType.DEPOSIT -> "Deposit"
        TransactionType.WITHDRAW -> "Withdraw"
    }
}

fun Transaction.matchesSearch(query: String): Boolean {
    val cleanQuery = query.trim()

    if (cleanQuery.isBlank()) {
        return true
    }

    return id.contains(cleanQuery, ignoreCase = true) ||
            note?.contains(cleanQuery, ignoreCase = true) == true ||
            amount.toString().contains(cleanQuery) ||
            type.name.contains(cleanQuery, ignoreCase = true)
}