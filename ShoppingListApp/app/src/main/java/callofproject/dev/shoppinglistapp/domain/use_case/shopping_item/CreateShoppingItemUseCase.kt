package callofproject.dev.shoppinglistapp.domain.use_case.shopping_item

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import javax.inject.Inject

class CreateShoppingItemUseCase @Inject constructor(private val mServiceHelper: ShoppingListServiceHelper) {

    suspend operator fun invoke(dto: ShoppingItemCreateDTO): ShoppingItem? {
        return mServiceHelper.createShoppingItem(dto)
    }
}