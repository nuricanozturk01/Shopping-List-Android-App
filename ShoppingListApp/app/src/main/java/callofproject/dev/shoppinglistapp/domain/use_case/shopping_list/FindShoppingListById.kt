package callofproject.dev.shoppinglistapp.domain.use_case.shopping_list

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import javax.inject.Inject

class FindShoppingListById @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(id: Long): ShoppingList? {
        return mServiceHelper.findShoppingListById(id)
    }
}