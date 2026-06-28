class BankAccount (
    val accountOwner: String,
    initialBalance: Double = 0.0
) {
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
        require(value = initialBalance >= 0.0) { "Initial balance can't be negative" }
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
}
