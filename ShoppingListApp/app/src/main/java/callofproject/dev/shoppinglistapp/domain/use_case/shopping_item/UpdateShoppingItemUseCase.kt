package callofproject.dev.shoppinglistapp.domain.use_case.shopping_item

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import javax.inject.Inject

class UpdateShoppingItemUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(item: ShoppingItem) = mServiceHelper.updateShoppingItem(item)
}