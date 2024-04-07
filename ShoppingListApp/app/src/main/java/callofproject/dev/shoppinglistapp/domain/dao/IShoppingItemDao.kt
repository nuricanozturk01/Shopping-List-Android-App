package callofproject.dev.shoppinglistapp.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem

@Dao
interface IShoppingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(item: ShoppingItem): Long

    @Delete
    fun remove(item: ShoppingItem)

    @Delete
    fun removeAll(items: List<ShoppingItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: ShoppingItem)


    @Query("select * from shopping_item where itemId = :id")
    fun findById(id: Long): ShoppingItem?
}