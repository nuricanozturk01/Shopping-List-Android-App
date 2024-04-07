package callofproject.dev.shoppinglistapp.presentation.shopping_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageState
import callofproject.dev.shoppinglistapp.route.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val mServiceHelper: ShoppingListServiceHelper
) : ViewModel() {

    private val _state = mutableStateOf<MainPageState>(MainPageState())
    val state: State<MainPageState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnClickSaveListBtn -> {
                saveShoppingList(event.name)
            }

            is ShoppingListEvent.OnClickCancelBtn -> {

            }
        }
    }


    private fun saveShoppingList(name: String) {
        viewModelScope.launch {
            val result = mServiceHelper.createShoppingList(name)

        }
    }

}