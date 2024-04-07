package callofproject.dev.shoppinglistapp.presentation.mainpage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent
import callofproject.dev.shoppinglistapp.route.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mServiceHelper: ShoppingListServiceHelper,
    private val mFormatter: DateTimeFormatter,
    private val mDateTime: LocalDateTime
) : ViewModel() {

    private val _state = mutableStateOf(MainPageState())
    val state: State<MainPageState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        findAll()
    }

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnClickSaveListBtn -> {
                viewModelScope.launch { saveShoppingList(event.name) }
            }

            is ShoppingListEvent.OnClickCancelBtn -> {

            }
        }
    }

    fun onEvent(event: MainPageEvent) {
        when (event) {
            is MainPageEvent.OnEditShoppingListClick -> {

            }

            is MainPageEvent.OnRemoveShoppingListClick -> {

            }
        }
    }

    private suspend fun saveShoppingList(name: String) {
        viewModelScope.launch {
            mServiceHelper.createShoppingList(name)
        }
    }

    private fun findAll() {
        viewModelScope.launch {
            _state.value.shoppingLists = mServiceHelper.findAllShoppingLists()
        }
    }

    fun toDateTimeStr(creationTime: LocalDateTime): String {
        return mFormatter.format(mDateTime)
    }


}