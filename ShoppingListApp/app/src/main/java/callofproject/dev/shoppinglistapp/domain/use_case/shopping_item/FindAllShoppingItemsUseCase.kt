package callofproject.dev.shoppinglistapp.domain.use_case.shopping_item

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllShoppingItemsUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(id: Long): Flow<Resource<List<ShoppingItem>>> {
        return mServiceHelper.findAllShoppingItemsByListId(id)
    }
}