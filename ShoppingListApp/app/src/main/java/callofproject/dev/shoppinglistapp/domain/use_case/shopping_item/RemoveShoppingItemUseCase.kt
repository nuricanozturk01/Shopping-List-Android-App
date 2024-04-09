package callofproject.dev.shoppinglistapp.domain.use_case.shopping_item

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import javax.inject.Inject

class RemoveShoppingItemUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(id: Long): Boolean = mServiceHelper.removeShoppingItemById(id)
}