package callofproject.dev.shoppinglistapp.data.dal

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ShoppingListServiceHelper @Inject constructor(
    private val mItemDao: IShoppingItemDao,
    private val mShoppingListDao: IShoppingListDao,
    private val formatter: DateTimeFormatter,
    private val dateTime: LocalDateTime
) {

    suspend fun createShoppingList(listName: String?) {
        try {
            if (listName == null) {
                val shoppingList =
                    ShoppingList(listName = "UNKNOWN LIST-${formatter.format(dateTime)}")

                mShoppingListDao.save(shoppingList)
            }

            mShoppingListDao.save(ShoppingList(listName = listName!!))
            Log.i("SUCCESS_SAVE", "YES")
        } catch (ignore: Exception) {
            Log.e("SAVE:ERROR", ignore.message!!)
        }
    }


    suspend fun createShoppingItem(item: ShoppingItemCreateDTO): ShoppingItemDTO? {
        return try {
            findShoppingItemById(mItemDao.save(item.toShoppingItem()))?.toShoppingItemDTO()
        } catch (ignore: Exception) {
            null
        }
    }

    private suspend fun findShoppingItemById(id: Long): ShoppingItem? {
        return try {
            mItemDao.findById(id)
        } catch (ignore: Exception) {
            null
        }
    }

    private suspend fun findShoppingListById(id: Long): ShoppingList? {
        return try {
            mShoppingListDao.findById(id)
        } catch (ignore: Exception) {
            null
        }
    }

    suspend fun findAllShoppingLists(): List<ShoppingList> {
        return try {
            mShoppingListDao.findAll()
        } catch (ignored: Exception) {
            emptyList()
        }

    }


    suspend fun removeShoppingItemById(id: Long): Boolean {
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