package callofproject.dev.shoppinglistapp.data.mapper

import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO

fun ShoppingItemCreateDTO.toShoppingItem(): ShoppingItem {
    return ShoppingItem(
        itemName = this.itemName,
        price = this.price,
        amount = this.amount,
        listId = this.listId
    )
}