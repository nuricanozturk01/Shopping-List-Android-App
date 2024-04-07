package callofproject.dev.shoppinglistapp.data.entity.ref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList

data class ShoppingListWithItems(
    @Embedded
    val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "itemId",
        associateBy = Junction(ShoppingItemListCrossRef::class)
    )
    val items: List<ShoppingItem>
)
