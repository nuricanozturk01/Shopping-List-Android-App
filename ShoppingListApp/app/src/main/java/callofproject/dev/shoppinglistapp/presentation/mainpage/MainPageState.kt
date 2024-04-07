package callofproject.dev.shoppinglistapp.presentation.mainpage

import callofproject.dev.shoppinglistapp.data.entity.ShoppingList


data class MainPageState(
    var shoppingLists: List<ShoppingList> = emptyList()
)