package callofproject.dev.shoppinglistapp.domain.use_case.shopping_list

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import javax.inject.Inject

class RemoveShoppingListUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(id: Long): Boolean = mServiceHelper.removeShoppingListById(id)
}