package com.example.bank_account_app.navigation

object AppRoutes {
    const val BANK_ACCOUNT = "bank_account"
    const val TRANSACTION_HISTORY = "transaction_history"
    const val TRANSACTION_DETAIL = "transaction_detail"
    const val TRANSACTION_ID = "transactionId"
    const val TRANSACTION_DETAIL_ROUTE = "$TRANSACTION_DETAIL/{$TRANSACTION_ID}"

    fun transactionDetailRoute(id: String): String {
        return "$TRANSACTION_DETAIL/$id"
    }
}