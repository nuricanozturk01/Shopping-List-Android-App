package callofproject.dev.shoppinglistapp.presentation.mainpage


import callofproject.dev.shoppinglistapp.data.entity.ShoppingList


data class MainPageState(
    val shoppingLists: List<ShoppingList> = emptyList(),
    val isLoading: Boolean = false
)