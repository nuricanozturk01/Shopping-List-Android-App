package callofproject.dev.shoppinglistapp.presentation.mainpage

import callofproject.dev.shoppinglistapp.data.entity.ShoppingList

sealed class MainPageEvent {
    data class OnEditShoppingListClick(val shoppingList: ShoppingList, val name: String) : MainPageEvent()
    data class OnRemoveShoppingListClick(val id: Long) : MainPageEvent()
    data object OnRefreshPage : MainPageEvent()
    data class OnClickSaveListBtn(val name: String) : MainPageEvent()
}