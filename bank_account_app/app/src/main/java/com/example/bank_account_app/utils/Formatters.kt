package com.example.bank_account_app.utils

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