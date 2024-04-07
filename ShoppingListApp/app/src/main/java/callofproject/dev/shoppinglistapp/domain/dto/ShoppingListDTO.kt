package callofproject.dev.shoppinglistapp.domain.dto

import java.time.LocalDateTime

data class ShoppingListDTO(
    val listName: String,
    val itemCount: Int,
    val creationTime: LocalDateTime
)

