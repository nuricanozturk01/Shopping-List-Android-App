package callofproject.dev.shoppinglistapp.presentation.shopping_list

import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem


data class ShoppingListState(
    val shoppingItemList: List<ShoppingItem> = emptyList(),
    var listId: Long = 0L,

)