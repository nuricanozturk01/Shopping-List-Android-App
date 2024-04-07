package callofproject.dev.shoppinglistapp.data.mapper

import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingListDTO

fun ShoppingList.toShoppingListDTO(): ShoppingListDTO {
    return ShoppingListDTO(this.listName, this.itemCount, this.creationTime)
}
