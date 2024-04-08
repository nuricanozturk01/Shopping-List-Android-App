package callofproject.dev.shoppinglistapp.presentation.shopping_list

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.dal.ShoppingListServiceHelper
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import callofproject.dev.shoppinglistapp.route.UiEvent
import callofproject.dev.shoppinglistapp.route.UiText
import callofproject.dev.shoppinglistapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val mServiceHelper: ShoppingListServiceHelper,
    mPreferences: IPreferences
) : ViewModel() {

    private val _state = mutableStateOf(ShoppingListState())
    val state: State<ShoppingListState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemName by mutableStateOf("")
    var amount by mutableStateOf("")
    var price by mutableStateOf("")

    val influxMoney = mutableStateOf("₺")
    val totalPrice = mutableStateOf("0")
    private var findAllJob: Job? = null

    init {
        influxMoney.value = mPreferences.getInfluxMoney()!!
    }

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnCreateItemClick -> {
                createShoppingItem(event.dto)
            }

            is ShoppingListEvent.OnRemoveItemClick -> removeItemById(event.itemId)

            is ShoppingListEvent.OnRefreshPage -> {}

            is ShoppingListEvent.OnEditShoppingItem -> updateShoppingItem(event.item)
        }
    }

    private fun updateShoppingItem(item: ShoppingItem) {
        viewModelScope.launch {
            mServiceHelper.updateShoppingItem(item)
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
        }
    }

    private fun removeItemById(itemId: Long) {
        viewModelScope.launch {
            val result = mServiceHelper.removeShoppingItemById(itemId)

            if (!result) {
                _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.info_list_removed_fail)))
                return@launch
            }
            _state.value = state.value.copy(shoppingItemList = state.value.shoppingItemList
                .filter { it.itemId != itemId })
            updateShoppingList(state.value.listId, -1)
            _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.info_list_removed_succes)))


        }
    }

    private fun createShoppingItem(dto: ShoppingItemCreateDTO) {
        viewModelScope.launch {
            val shoppingItem = mServiceHelper.createShoppingItem(dto)
            updateShoppingList(shoppingItem!!.listId, 1)
            _state.value = state.value.copy(
                shoppingItemList = state.value.shoppingItemList + shoppingItem
            )

            _uiEvent.send(UiEvent.ShowToastMessage(UiText.StringResource(R.string.msg_list_added)))
        }
    }

    private fun updateShoppingList(listId: Long, counter: Int) {
        viewModelScope.launch {
            val list = mServiceHelper.findShoppingListById(listId)
            if (counter > 0)
                list!!.itemCount = list.itemCount + 1

            else list!!.itemCount = list.itemCount - 1


            mServiceHelper.updateShoppingList(list)
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
            mServiceHelper.findAllShoppingItemsByListId(id).onEach { result ->
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

    fun evaluate(price: Float, amount: Int): String = (price * amount).toString()
    fun evaluateTotalPrice() {
        totalPrice.value =
            state.value.shoppingItemList.map { it.price * it.amount }.sum().toString()
    }

    fun emptyItem(): ShoppingItem {
        return ShoppingItem()
    }
}