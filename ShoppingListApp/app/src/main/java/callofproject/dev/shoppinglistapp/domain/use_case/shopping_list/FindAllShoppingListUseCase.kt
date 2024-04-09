package callofproject.dev.shoppinglistapp.domain.use_case.shopping_list

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllShoppingListUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {
    suspend operator fun invoke(): Flow<Resource<List<ShoppingList>>> {
        return mServiceHelper.findAllShoppingLists()
    }
}