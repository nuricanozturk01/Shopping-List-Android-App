package callofproject.dev.shoppinglistapp.di.module

import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.domain.use_case.ShoppingListAppUseCasesFacade
import callofproject.dev.shoppinglistapp.domain.use_case.StrLengthUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.CreateShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.FindAllShoppingItemsUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.RemoveShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_item.UpdateShoppingItemUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.CreateShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.FindAllShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.FindShoppingListById
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.RemoveShoppingListUseCase
import callofproject.dev.shoppinglistapp.domain.use_case.shopping_list.UpdateShoppingListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideShoppingListUseCaseFacade(serviceHelper: ShoppingListServiceHelper): ShoppingListAppUseCasesFacade {
        return ShoppingListAppUseCasesFacade(
            createShoppingItem = CreateShoppingItemUseCase(serviceHelper),
            findAllShoppingItems = FindAllShoppingItemsUseCase(serviceHelper),
            removeShoppingItem = RemoveShoppingItemUseCase(serviceHelper),
            updateShoppingItem = UpdateShoppingItemUseCase(serviceHelper),
            createShoppingList = CreateShoppingListUseCase(serviceHelper),
            findAllShoppingList = FindAllShoppingListUseCase(serviceHelper),
            findShoppingListById = FindShoppingListById(serviceHelper),
            removeShoppingList = RemoveShoppingListUseCase(serviceHelper),
            updateShoppingList = UpdateShoppingListUseCase(serviceHelper),
            stringValidator = StrLengthUseCase()
        )
    }
}