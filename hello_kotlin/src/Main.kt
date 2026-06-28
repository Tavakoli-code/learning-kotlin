
fun main() {
    val account1 = BankAccount("Sajad Ali Tavakoli")
    account1.deposit(amount = 1000.0)
    account1.pay(amount = 50.0)
    println(account1.displayBalance)
}