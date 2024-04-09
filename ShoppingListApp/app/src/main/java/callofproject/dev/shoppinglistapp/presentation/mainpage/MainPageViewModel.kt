package callofproject.dev.shoppinglistapp.presentation.mainpage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.domain.use_case.ShoppingListAppUseCasesFacade
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent.OnClickSaveListBtn
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent.OnEditShoppingListClick
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent.OnRefreshPage
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent.OnRemoveShoppingListClick
import callofproject.dev.shoppinglistapp.util.Resource
import callofproject.dev.shoppinglistapp.util.route.UiEvent
import callofproject.dev.shoppinglistapp.util.route.UiEvent.ShowSnackbar
import callofproject.dev.shoppinglistapp.util.route.UiEvent.ShowToastMessage
import callofproject.dev.shoppinglistapp.util.route.UiText.StringResource
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
    private val mUseCasesFacade: ShoppingListAppUseCasesFacade,
    private val mFormatter: DateTimeFormatter
) : ViewModel() {

    private var _state = mutableStateOf(MainPageState())
    val state: State<MainPageState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var findAllJob: Job? = null


    fun onEvent(event: MainPageEvent) = when (event) {
        is OnClickSaveListBtn -> saveShoppingList(event.name)

        is OnRemoveShoppingListClick -> removeList(event.id)

        is OnRefreshPage -> findAll()

        is OnEditShoppingListClick -> updateShoppingList(event.shoppingList, event.name)
    }

    private fun updateShoppingList(shoppingList: ShoppingList, name: String) {
        viewModelScope.launch {

            if (!mUseCasesFacade.stringValidator(name)) {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.message_validate_str)))
                return@launch
            }

            shoppingList.listName = name
            mUseCasesFacade.updateShoppingList(shoppingList)

            _state.value = state.value.copy(
                shoppingLists = state.value.shoppingLists.map {
                    if (shoppingList.listId == it.listId)
                        it.listName = name
                    it
                }
            )

            _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_list_updated)))
        }
    }

    private fun removeList(id: Long) {
        viewModelScope.launch {
            val result = mUseCasesFacade.removeShoppingList(id)

            if (!result) {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.message_validate_str)))
                return@launch
            }
            _uiEvent.send(ShowToastMessage(StringResource(R.string.info_list_removed_succes)))

            _state.value = state.value.copy(shoppingLists = state.value.shoppingLists
                .filter { it.listId != id })

            _uiEvent.send(ShowSnackbar(StringResource(R.string.msg_item_removed)))
        }
    }

    private fun saveShoppingList(name: String) {
        viewModelScope.launch {

            if (!mUseCasesFacade.stringValidator(name)) {
                _uiEvent.send(ShowToastMessage(StringResource(R.string.message_validate_str)))
                return@launch
            }

            val shoppingList = mUseCasesFacade.createShoppingList(name)

            _state.value = state.value.copy(
                shoppingLists = state.value.shoppingLists + shoppingList!!
            )

            _uiEvent.send(ShowToastMessage(StringResource(R.string.msg_list_added)))
        }
    }

    fun findAll() {
        findAllJob?.cancel()
        findAllJob = viewModelScope.launch {
            mUseCasesFacade.findAllShoppingList().onEach { result ->
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