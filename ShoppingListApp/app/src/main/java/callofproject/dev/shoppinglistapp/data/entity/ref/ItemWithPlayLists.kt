package callofproject.dev.shoppinglistapp.data.entity.ref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList


data class ItemWithPlayLists(
    @Embedded
    val item: ShoppingItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "listId",
        associateBy = Junction(ShoppingItemListCrossRef::class)
    )
    val shoppingLists: List<ShoppingList>
)
