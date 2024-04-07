package callofproject.dev.shoppinglistapp.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface IShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: ShoppingList)

    @Delete
    suspend fun remove(list: ShoppingList)

    @Delete
    suspend fun removeAll(lists: List<ShoppingList>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(list: ShoppingList)

    @Query("select * from shopping_list where listId = :id")
    suspend fun findById(id: Long): ShoppingList?

    @Query("select * from shopping_list")
    suspend fun findAll(): List<ShoppingList>
}