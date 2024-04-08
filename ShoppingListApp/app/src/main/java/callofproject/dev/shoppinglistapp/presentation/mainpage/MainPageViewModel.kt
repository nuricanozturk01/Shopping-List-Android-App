package callofproject.dev.shoppinglistapp.presentation.mainpage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.route.UiEvent
import callofproject.dev.shoppinglistapp.route.UiEvent.ShowSnackbar
import callofproject.dev.shoppinglistapp.route.UiText.StringResource
import callofproject.dev.shoppinglistapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mServiceHelper: ShoppingListServiceHelper,
    private val mFormatter: DateTimeFormatter
) : ViewModel() {

    private var _state = mutableStateOf(MainPageState())
    val state: State<MainPageState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var findAllJob: Job? = null

    init {
        findAll()
    }

    fun onEvent(event: MainPageEvent) = when (event) {
        is MainPageEvent.OnClickSaveListBtn -> saveShoppingList(event.name)

        is MainPageEvent.OnRemoveShoppingListClick -> removeList(event.id)

        is MainPageEvent.OnRefreshPage -> findAll()

        is MainPageEvent.OnEditShoppingListClick -> {

        }
    }

    private fun removeList(id: Long) {
        viewModelScope.launch {
            val result = mServiceHelper.removeShoppingListById(id)

            if (!result) {
                _uiEvent.send(UiEvent.ShowToastMessage(StringResource(R.string.info_list_removed_fail)))
                return@launch
            }
            _uiEvent.send(UiEvent.ShowToastMessage(StringResource(R.string.info_list_removed_succes)))

            _state.value = state.value.copy(shoppingLists = state.value.shoppingLists
                .filter { it.listId != id })
        }
    }

    private fun saveShoppingList(name: String) {
        viewModelScope.launch {
            val shoppingList = mServiceHelper.createShoppingList(name)

            _state.value = state.value.copy(
                shoppingLists = state.value.shoppingLists + shoppingList!!
            )

            _uiEvent.send(UiEvent.ShowToastMessage(StringResource(R.string.msg_list_added)))
        }
    }

    private fun findAll() {
        findAllJob?.cancel()
        findAllJob = viewModelScope.launch {
            mServiceHelper.findAllShoppingLists().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            shoppingLists = emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            shoppingLists = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _uiEvent.send(ShowSnackbar(StringResource(R.string.error_find_shopping_list)))
                    }
                }
            }.launchIn(this)
        }
    }


    fun toDateTimeStr(creationTime: LocalDateTime): String {
        return mFormatter.format(creationTime)
    }
}