package callofproject.dev.shoppinglistapp.domain.use_case.shopping_list

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import javax.inject.Inject

class UpdateShoppingListUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(item: ShoppingList) {
        mServiceHelper.updateShoppingList(item)
    }
}