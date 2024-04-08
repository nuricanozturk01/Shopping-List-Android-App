package callofproject.dev.shoppinglistapp.domain.dto

data class ShoppingItemCreateDTO(
    val itemName: String,
    val price: Float,
    val amount: Int,
    val listId: Long
)
