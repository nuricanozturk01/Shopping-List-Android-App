package callofproject.dev.shoppinglistapp.domain.use_case.shopping_list

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import javax.inject.Inject

class CreateShoppingListUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(listName: String): ShoppingList? {
        return mServiceHelper.createShoppingList(listName)
    }
}