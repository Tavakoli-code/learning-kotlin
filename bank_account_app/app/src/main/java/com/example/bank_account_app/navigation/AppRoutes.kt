package com.example.bank_account_app.navigation

object AppRoutes {
    const val BANK_ACCOUNT = "bank_account"
    const val TRANSACTION_HISTORY = "transaction_history"
    const val TRANSACTION_DETAIL = "transaction_detail"
    const val TRANSACTION_INDEX = "transactionIndex"
    const val TRANSACTION_DETAIL_ROUTE = "$TRANSACTION_DETAIL/{$TRANSACTION_INDEX}"

    fun transactionDetailRoute(index: Int): String {
        return "$TRANSACTION_DETAIL/$index"
    }
}