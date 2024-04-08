package callofproject.dev.shoppinglistapp.presentation.mainpage

sealed class MainPageEvent {
    data object OnEditShoppingListClick : MainPageEvent()
    data class OnRemoveShoppingListClick(val id: Long) : MainPageEvent()
    data object OnRefreshPage : MainPageEvent()
    data class OnClickSaveListBtn(val name: String) : MainPageEvent()
}