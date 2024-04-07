package callofproject.dev.shoppinglistapp.data.dal

import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.data.mapper.toShoppingItem
import callofproject.dev.shoppinglistapp.data.mapper.toShoppingItemDTO
import callofproject.dev.shoppinglistapp.data.mapper.toShoppingListDTO
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingItemDao
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingListDao
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemDTO
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingListDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ShoppingListServiceHelper @Inject constructor(
    private val mItemDao: IShoppingItemDao,
    private val mShoppingListDao: IShoppingListDao,
    private val formatter: DateTimeFormatter,
    private val dateTime: LocalDateTime
) {

    fun createShoppingList(listName: String?): ShoppingListDTO? {
        try {
            if (listName == null) {
                val shoppingList =
                    ShoppingList(listName = "UNKNOWN LIST-${formatter.format(dateTime)}")

                return findShoppingListById(mShoppingListDao.save(shoppingList))?.toShoppingListDTO()
            }

            return findShoppingListById(mShoppingListDao.save(ShoppingList(listName = listName)))?.toShoppingListDTO()

        } catch (ignore: Exception) {
            return null
        }
    }


    fun createShoppingItem(item: ShoppingItemCreateDTO): ShoppingItemDTO? {
        return try {
            findShoppingItemById(mItemDao.save(item.toShoppingItem()))?.toShoppingItemDTO()
        } catch (ignore: Exception) {
            null
        }
    }

    private fun findShoppingItemById(id: Long): ShoppingItem? {
        return try {
            mItemDao.findById(id)
        } catch (ignore: Exception) {
            null
        }
    }

    private fun findShoppingListById(id: Long): ShoppingList? {
        return try {
            mShoppingListDao.findById(id)
        } catch (ignore: Exception) {
            null
        }
    }


    fun removeShoppingItemById(id: Long): Boolean {
        return try {
            val item = mItemDao.findById(id)
            if (item != null) {
                mItemDao.remove(item)
            }
            true
        } catch (ignore: Exception) {
            false
        }
    }
}