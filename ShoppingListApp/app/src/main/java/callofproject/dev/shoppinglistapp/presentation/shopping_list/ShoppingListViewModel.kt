package callofproject.dev.shoppinglistapp.presentation.shopping_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import callofproject.dev.shoppinglistapp.domain.use_case.ShoppingListAppUseCasesFacade
import callofproject.dev.shoppinglistapp.util.Resource
import callofproject.dev.shoppinglistapp.util.route.UiEvent
import callofproject.dev.shoppinglistapp.util.route.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val mUsesCases: ShoppingListAppUseCasesFacade,
    mPreferences: IPreferences
) : ViewModel() {

    private val _state = mutableStateOf(ShoppingListState())
    val state: State<ShoppingListState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val influxMoney = mutableStateOf("₺")
    val totalPrice = mutableStateOf("0")
    private var findAllJob: Job? = null

    init {
        influxMoney.value = mPreferences.getInfluxMoney()!!
    }

    fun onEvent(event: ShoppingListEvent) = when (event) {
        is ShoppingListEvent.OnCreateItemClick -> createShoppingItem(event.dto)

        is ShoppingListEvent.OnRemoveItemClick -> removeItemById(event.itemId)

        is ShoppingListEvent.OnRefreshPage -> findAll(state.value.listId)

        is ShoppingListEvent.OnEditShoppingItem -> updateShoppingItem(
            event.item,
            event.name,
            event.amount,
            event.price
        )
    }

    private fun updateShoppingItem(
        item: ShoppingItem,
        name: String,
        amount: String,
        price: String
    ) {
        viewModelScope.launch {

            if (!mUsesCases.stringValidator(name)) {
                _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.message_validate_str)))
                return@launch
            }

            item.itemName = name
            item.amount = amount.toInt()
            item.price = price.toFloat()
            mUsesCases.updateShoppingItem(item)

            _state.value = state.value.copy(
                shoppingItemList = state.value.shoppingItemList
                    .map {
                        if (it.itemId == item.itemId) {
                            it.itemName = item.itemName
                            it.amount = item.amount
                            it.price = item.price
                        }
                        it
                    }
            )

            evaluateTotalPrice()
            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.msg_item_updated)))
        }
    }

    private fun removeItemById(itemId: Long) {
        viewModelScope.launch {
            val result = mUsesCases.removeShoppingItem(itemId)

            if (!result) {
                _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.info_list_removed_fail)))
                return@launch
            }
            _state.value = state.value.copy(shoppingItemList = state.value.shoppingItemList
                .filter { it.itemId != itemId })
            updateShoppingList(state.value.listId, -1)
            _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.info_item_removed_succes)))


        }
    }

    private fun createShoppingItem(dto: ShoppingItemCreateDTO) {
        viewModelScope.launch {

            if (!mUsesCases.stringValidator(dto.itemName)) {
                _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.message_validate_str)))
                return@launch
            }

            val shoppingItem = mUsesCases.createShoppingItem(dto)
            updateShoppingList(shoppingItem!!.listId, 1)

            _state.value = state.value.copy(
                shoppingItemList = state.value.shoppingItemList + shoppingItem
            )

            _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.msg_item_added)))
        }
    }

    private fun updateShoppingList(listId: Long, counter: Int) {
        viewModelScope.launch {
            val list = mUsesCases.findShoppingListById(listId)
            if (counter > 0)
                list!!.itemCount = list.itemCount + 1
            else list!!.itemCount = list.itemCount - 1

            mUsesCases.updateShoppingList(list)
        }
    }

    fun setListId(shoppingListId: Long) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                listId = shoppingListId,
                shoppingItemList = state.value.shoppingItemList
            )
        }
    }

    fun findAll(id: Long) {
        findAllJob?.cancel()
        findAllJob = viewModelScope.launch {
            mUsesCases.findAllShoppingItems(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            shoppingItemList = emptyList(),
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            shoppingItemList = result.data ?: emptyList(),
                        )
                        evaluateTotalPrice()
                    }

                    is Resource.Error -> {
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_find_shopping_list)))
                    }
                }
            }.launchIn(this)
        }
    }

    fun evaluateTotalPrice() {
        totalPrice.value =
            state.value.shoppingItemList.map { it.price * it.amount }.sum().toString()
    }


}