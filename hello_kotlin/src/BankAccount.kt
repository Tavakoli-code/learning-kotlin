class BankAccount (
    val accountOwner: String,
    initialBalance: Double = 0.0,
    val accountType: AccountType = AccountType.SAVING
) {
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
        get() = "%.2f AFN".format(balance)

    init {
        require(value = accountOwner.isNotBlank()) { "Account owner must not be blank" }
        require(value = initialBalance >= MIN_INITIAL_BALANCE) { "Initial balance can't be negative" }
    }

    fun deposit(amount: Double): Boolean {
        if (amount > 0.0) {
            this.balance += amount
            return true
        }
        return false
    }

    fun withdraw(amount: Double): Boolean {
        if (canWithdraw && amount <= this.balance) {
            this.balance -= amount
            return true
        }
        return false
    }

    fun printAccountType (accountType: AccountType) {
        when (accountType) {
            AccountType.SAVING -> println("Saving account")
            AccountType.BUSINESS -> println("BUSINESS account")
            AccountType.CURRENT -> println("Current account")
        }
    }
}

enum class AccountType {
    SAVING,
    CURRENT,
    BUSINESS,
}