package callofproject.dev.shoppinglistapp.di.module

import android.app.Application
import androidx.room.Room
import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.database.ShoppingApplicationDatabase
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingItemDao
import callofproject.dev.shoppinglistapp.domain.dao.IShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShoppingListDatabaseModule {

    @Provides
    @Singleton
    fun provideShoppingListDatabase(app: Application): ShoppingApplicationDatabase {
        return Room.databaseBuilder(
            app,
            ShoppingApplicationDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShoppingItemDao(db: ShoppingApplicationDatabase): IShoppingItemDao {
        return db.createShoppingItemDao()
    }

    @Provides
    @Singleton
    fun provideShoppingListDao(db: ShoppingApplicationDatabase): IShoppingListDao {
        return db.createShoppingListDao()
    }


    @Provides
    @Singleton
    fun provideServiceHelper(
        itemDao: IShoppingItemDao,
        listDao: IShoppingListDao,
        formatter: DateTimeFormatter,
        dateTime: LocalDateTime
    ): ShoppingListServiceHelper {
        return ShoppingListServiceHelper(itemDao, listDao, formatter, dateTime)
    }
}