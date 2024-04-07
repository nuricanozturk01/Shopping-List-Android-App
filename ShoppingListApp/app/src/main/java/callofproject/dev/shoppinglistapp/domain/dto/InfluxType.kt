package callofproject.dev.shoppinglistapp.domain.dto

sealed class InfluxType(val name: String) {
    data object MoneyTL : InfluxType("₺")
    data object MoneyEUR : InfluxType("£")
    data object MoneyUSD : InfluxType("$")

    companion object {
        fun fromString(name: String): InfluxType {
            return when (name) {
                "₺" -> MoneyTL
                "£" -> MoneyEUR
                else -> MoneyUSD
            }
        }
    }
}