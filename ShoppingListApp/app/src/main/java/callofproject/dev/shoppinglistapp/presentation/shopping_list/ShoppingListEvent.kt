package callofproject.dev.shoppinglistapp.presentation.shopping_list

sealed class ShoppingListEvent {
    data class OnClickSaveListBtn(val name: String) : ShoppingListEvent()
    data object OnClickCancelBtn : ShoppingListEvent()
}