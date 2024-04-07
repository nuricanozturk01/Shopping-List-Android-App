package callofproject.dev.shoppinglistapp.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList

@Dao
interface IShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(list: ShoppingList) : Long

    @Delete
    fun remove(list: ShoppingList)

    @Delete
    fun removeAll(lists: List<ShoppingList>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(list: ShoppingList)

    @Query("select * from shopping_list where listId = :id")
    fun findById(id: Long): ShoppingList?
}