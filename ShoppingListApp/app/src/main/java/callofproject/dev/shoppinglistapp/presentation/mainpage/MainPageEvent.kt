package callofproject.dev.shoppinglistapp.presentation.mainpage

sealed class MainPageEvent {
    data object OnEditShoppingListClick : MainPageEvent()
    data object OnRemoveShoppingListClick : MainPageEvent()
}