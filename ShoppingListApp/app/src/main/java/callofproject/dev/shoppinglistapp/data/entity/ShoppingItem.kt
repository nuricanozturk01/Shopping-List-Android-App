package callofproject.dev.shoppinglistapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("shopping_item")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,
    @ColumnInfo("item_name")
    val itemName: String = "NO_NAME",
    @ColumnInfo("price")
    val price: Float = 0F,
    @ColumnInfo("amount")
    val amount: Int = 0
) : Serializable
