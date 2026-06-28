class BankAccount(
    val accountOwner: String,
    initialBalance: Double = 0.0,
    val accountType: AccountType = AccountType.SAVING
) : Payable {

    companion object {
        const val MIN_INITIAL_BALANCE = 0.0
        const val CURRENCY = "AFN"
    }

    var balance: Double = initialBalance
        private set

    val isEmpty: Boolean
        get() = balance == 0.0

    val canWithdraw: Boolean
        get() = balance > 0.0

    val displayBalance: String
        get() = "%.2f $CURRENCY".format(balance)

    val accountTypeLabel: String
        get() = when (accountType) {
            AccountType.SAVING -> "Saving account"
            AccountType.CURRENT -> "Current account"
            AccountType.BUSINESS -> "Business account"
        }

    init {
        require(accountOwner.isNotBlank()) { "Account owner must not be blank" }
        require(initialBalance >= MIN_INITIAL_BALANCE) { "Initial balance can't be negative" }
    }

    fun deposit(amount: Double): TransactionResult {
        if (amount <= 0.0) {
            return TransactionResult.Failed("Deposit amount must be positive")
        }

        balance += amount
        return TransactionResult.Success(balance)
    }

    fun withdraw(amount: Double): TransactionResult {
        if (amount <= 0.0) {
            return TransactionResult.Failed("Withdraw amount must be positive")
        }

        if (amount > balance) {
            return TransactionResult.Failed("Insufficient balance")
        }

        balance -= amount
        return TransactionResult.Success(balance)
    }

    override fun pay(amount: Double): TransactionResult {
        return withdraw(amount)
    }
}

enum class AccountType {
    SAVING,
    CURRENT,
    BUSINESS
}

interface Payable {
    fun pay(amount: Double): TransactionResult
}

sealed class TransactionResult {
    data class Success(val newBalance: Double) : TransactionResult()
    data class Failed(val reason: String) : TransactionResult()
}