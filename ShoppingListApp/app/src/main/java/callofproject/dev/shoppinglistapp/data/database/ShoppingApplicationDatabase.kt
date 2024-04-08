package callofproject.dev.shoppinglistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import callofproject.dev.shoppinglistapp.data.converter.LocalDateTimeConverter
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingItemDao
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingListDao

@Database(
    entities = [ShoppingItem::class, ShoppingList::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ShoppingApplicationDatabase : RoomDatabase() {
    abstract fun createShoppingItemDao(): IShoppingItemDao
    abstract fun createShoppingListDao(): IShoppingListDao
}