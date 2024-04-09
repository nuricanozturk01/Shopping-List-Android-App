package callofproject.dev.shoppinglistapp.domain.use_case

import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.CreateShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.FindAllShoppingItemsUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.RemoveShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.UpdateShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.CreateShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.FindAllShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.FindShoppingListById
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.RemoveShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.UpdateShoppingListUseCase

data class ShoppingListAppUseCasesFacade(
    val createShoppingItem: CreateShoppingItemUseCase,
    val findAllShoppingItems: FindAllShoppingItemsUseCase,
    val removeShoppingItem: RemoveShoppingItemUseCase,
    val updateShoppingItem: UpdateShoppingItemUseCase,
    val createShoppingList: CreateShoppingListUseCase,
    val findAllShoppingList: FindAllShoppingListUseCase,
    val findShoppingListById: FindShoppingListById,
    val removeShoppingList: RemoveShoppingListUseCase,
    val updateShoppingList: UpdateShoppingListUseCase,
    val stringValidator: StrLengthUseCase
)