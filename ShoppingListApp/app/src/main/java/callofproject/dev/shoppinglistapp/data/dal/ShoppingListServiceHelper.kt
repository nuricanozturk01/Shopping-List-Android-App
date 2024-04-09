package callofproject.dev.shoppinglistapp.data.dal

import android.util.Log
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.data.mapper.toShoppingItem
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingItemDao
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingListDao
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.util.DAL_ERROR_ITEM
import callofproject.dev.shoppinglistapp.util.DAL_ERROR_LIST
import callofproject.dev.shoppinglistapp.util.DAL_SUCCESS_ITEM
import callofproject.dev.shoppinglistapp.util.DAL_SUCCESS_LIST
import callofproject.dev.shoppinglistapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ShoppingListServiceHelper @Inject constructor(
    private val mItemDao: IShoppingItemDao,
    private val mShoppingListDao: IShoppingListDao,
    private val formatter: DateTimeFormatter,
    private val dateTime: LocalDateTime
) {

    suspend fun createShoppingList(listName: String?): ShoppingList? {
        try {
            if (listName == null) {
                val shoppingList =
                    ShoppingList(listName = "UNKNOWN LIST-${formatter.format(dateTime)}")

                val listId = mShoppingListDao.save(shoppingList)
                return findShoppingListById(listId)!!
            }

            return findShoppingListById(mShoppingListDao.save(ShoppingList(listName = listName)))!!
        } catch (exception: Exception) {
            return null
        }
    }

    suspend fun createShoppingItem(item: ShoppingItemCreateDTO): ShoppingItem? {
        return try {
            findShoppingItemById(mItemDao.save(item.toShoppingItem()))
        } catch (exception: Exception) {
            null
        }
    }

    suspend fun updateShoppingList(list: ShoppingList) {
        try {
            mShoppingListDao.update(list)
        } catch (ignored: Throwable) {
        }
    }


    private suspend fun findShoppingItemById(id: Long): ShoppingItem? {
        return try {
            mItemDao.findById(id)
        } catch (exception: Exception) {
            null
        }
    }

    suspend fun findShoppingListById(id: Long): ShoppingList? {
        return try {
            mShoppingListDao.findById(id)
        } catch (exception: Exception) {
            null
        }
    }


    suspend fun findAllShoppingLists(): Flow<Resource<List<ShoppingList>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(mShoppingListDao.findAll()))
        } catch (exception: Throwable) {
            emit(Resource.Error("Something wrong!..."))
        }
    }

    suspend fun findAllShoppingItemsByListId(id: Long): Flow<Resource<List<ShoppingItem>>> =
        flow {
            try {
                emit(Resource.Loading())
                val items = mItemDao.findAllItemsByListId(id)
                emit(Resource.Success(items))
            } catch (exception: Throwable) {
                emit(Resource.Error("Something wrong!..."))
            }
        }


    suspend fun removeShoppingItemById(id: Long): Boolean {
        return try {
            val item = mItemDao.findById(id)
            if (item != null) {
                mItemDao.remove(item)
                Log.i(DAL_SUCCESS_ITEM, "Shopping Item Removed successfully!")
                true
            } else {
                Log.e(DAL_SUCCESS_ITEM, "Shopping Item Remove Error!!")
                false
            }
        } catch (exception: Exception) {
            Log.e(DAL_ERROR_ITEM, exception.message!!)
            false
        }
    }

    suspend fun removeShoppingListById(id: Long): Boolean {
        return try {
            val item = mShoppingListDao.findById(id)
            if (item != null) {
                mShoppingListDao.remove(item)
                Log.i(DAL_SUCCESS_LIST, "Shopping List Removed successfully!")
                true
            } else {
                Log.e(DAL_SUCCESS_LIST, "Shopping List Remove Error!!")
                false
            }
        } catch (exception: Exception) {
            Log.e(DAL_ERROR_LIST, exception.message!!)
            false
        }
    }

    suspend fun updateShoppingItem(item: ShoppingItem) {
        try {
            mItemDao.update(item)
            Log.i(DAL_SUCCESS_ITEM, "item updated successfully!")

        } catch (exception: Throwable) {
            Log.e(DAL_ERROR_ITEM, exception.message!!)
        }
    }
}