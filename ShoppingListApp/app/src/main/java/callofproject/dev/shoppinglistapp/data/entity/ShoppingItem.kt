package callofproject.dev.shoppinglistapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "shopping_item",
    indices = [Index("list_id")],
    foreignKeys = [ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["list_id"],
        childColumns = ["list_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,
    @ColumnInfo("list_id")
    val listId: Long = 0L,
    @ColumnInfo("item_name")
    val itemName: String = "NO_NAME",
    @ColumnInfo("price")
    val price: Float = 0F,
    @ColumnInfo("amount")
    val amount: Int = 0
) : Serializable
