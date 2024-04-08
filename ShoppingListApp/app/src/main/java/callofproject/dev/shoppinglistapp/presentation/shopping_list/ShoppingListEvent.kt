package callofproject.dev.shoppinglistapp.presentation.shopping_list

import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO


sealed class ShoppingListEvent {
    data object OnRefreshPage : ShoppingListEvent()
    data class OnRemoveItemClick(val itemId: Long) : ShoppingListEvent()
    data class OnCreateItemClick(val dto: ShoppingItemCreateDTO) : ShoppingListEvent()

}