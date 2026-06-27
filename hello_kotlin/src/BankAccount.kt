class BankAccount (
    val accountOwner: String,
    balance: Double = 0.0
) {
    var balance: Double = balance
        private set

    init {
        require(accountOwner.isNotBlank()) { "Account owner must not be blank" }
        require(balance >= 0.0) { "Initial balance can't be negative" }
    }

    fun deposit(amount: Double) {
        if (amount > 0.0) {
            this.balance += amount
            println("Deposit successful. New balance: $this.balance")
        } else {
            println("Deposit failed. Deposit amount must be positive")
        }
    }

    fun withdraw(amount: Double) {
        if (amount > 0.0 && amount <= this.balance) {
            this.balance -= amount
            println("Withdraw successful. New balance: $this.balance")
        } else {
            println("Withdraw failed. Withdraw amount must be positive and not greater than balance amount")
        }
    }
}
