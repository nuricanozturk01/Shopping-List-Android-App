package callofproject.dev.shoppinglistapp.data.entity.ref

import androidx.room.Entity

@Entity(primaryKeys = ["itemId", "listId"])
data class ShoppingItemListCrossRef(
    val itemId: Long,
    val listId: Long
)
