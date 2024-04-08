package callofproject.dev.shoppinglistapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity("shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("list_id")
    val listId: Long = 0L,
    @ColumnInfo("list_name")
    val listName: String = "",
    @ColumnInfo("item_count")
    var itemCount: Int = 0,
    @ColumnInfo("creation_time")
    val creationTime: LocalDateTime = now(),
    @ColumnInfo("total_price")
    val totalPrice: Float = 0F
)
