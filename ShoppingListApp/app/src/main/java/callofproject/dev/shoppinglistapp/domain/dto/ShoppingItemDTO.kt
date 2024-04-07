package callofproject.dev.shoppinglistapp.domain.dto

data class ShoppingItemDTO(
    val itemName: String,
    val price: Float,
    val amount: Int,
    val totalPrice: Float
)
