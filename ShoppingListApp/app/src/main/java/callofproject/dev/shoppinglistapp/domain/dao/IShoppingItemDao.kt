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
    suspend fun save(item: ShoppingItem): Long

    @Delete
    suspend fun remove(item: ShoppingItem)

    @Delete
    suspend fun removeAll(items: List<ShoppingItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ShoppingItem)


    @Query("select * from shopping_item where itemId = :id")
    suspend fun findById(id: Long): ShoppingItem?

    @Query(
        """SELECT * FROM shopping_list sl INNER JOIN shopping_item si on sl.list_id = si.list_id 
            WHERE sl.list_id = :listId order by si.list_id desc
    """
    )
    suspend fun findAllItemsByListId(listId: Long): List<ShoppingItem>
}