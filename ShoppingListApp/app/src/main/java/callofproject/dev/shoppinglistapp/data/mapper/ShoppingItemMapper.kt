package callofproject.dev.shoppinglistapp.data.mapper

import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemDTO

fun ShoppingItemCreateDTO.toShoppingItem(): ShoppingItem {
    return ShoppingItem(itemName = this.itemName, price = this.price, amount = this.amount)
}

fun ShoppingItem.toShoppingItemDTO(): ShoppingItemDTO {
    return ShoppingItemDTO(this.itemName, this.price, this.amount, this.amount * this.price)
}
