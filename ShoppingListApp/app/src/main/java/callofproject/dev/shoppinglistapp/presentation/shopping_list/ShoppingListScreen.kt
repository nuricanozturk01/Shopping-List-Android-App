package callofproject.dev.shoppinglistapp.presentation.shopping_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent.OnRefreshPage
import callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item.ShoppingItemScreen
import callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item.ShoppingItemUpsertScreen
import callofproject.dev.shoppinglistapp.util.route.UiEvent

@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    shoppingListId: Long,
    scaffoldState: SnackbarHostState
) {
    viewModel.setListId(shoppingListId)
    var expandedCreateModal by remember { mutableStateOf(false) }

    val state = viewModel.state.value
    val context = LocalContext.current


    DisposableEffect(key1 = true) {
        viewModel.findAll(shoppingListId)
        onDispose { }
    }

    LaunchedEffect(key1 = state.shoppingItemList) {
        viewModel.evaluateTotalPrice() // insert or remove
    }


    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // Doing nothing
        TopBarComponent(confirmEvent = {
            Toast.makeText(
                context,
                context.getString(R.string.not_supported_operartion),
                Toast.LENGTH_SHORT
            ).show()
        })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(45))
                .padding(10.dp)
                .height(50.dp)
                .background(MaterialTheme.colorScheme.onSecondary)
                .shadow(.5.dp, clip = true, shape = RoundedCornerShape(1.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.total_price),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight(700)
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "${viewModel.influxMoney.value} ${viewModel.totalPrice.value}",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.width(130.dp))
            ElevatedButton(
                onClick = {
                    expandedCreateModal = true
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }

        if (expandedCreateModal) {
            ShoppingItemUpsertScreen(
                onDismissRequest = { expandedCreateModal = false },
                title = stringResource(R.string.create_item_menu),
                confirmEvent = { name, amount, price ->
                    val dto = ShoppingItemCreateDTO(name, price.toFloat(), amount.toInt(), shoppingListId)
                    viewModel.onEvent(ShoppingListEvent.OnCreateItemClick(dto))
                }
            )
        }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (state.shoppingItemList.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {
            if (state.shoppingItemList.isNotEmpty()) {

                items(state.shoppingItemList.size) { idx ->
                    ShoppingItemScreen(
                        item = state.shoppingItemList[idx],
                        influxMoney = viewModel.influxMoney.value,
                        onDeleteClick = {
                            viewModel.onEvent(
                                ShoppingListEvent.OnRemoveItemClick(
                                    state.shoppingItemList[idx].itemId
                                )
                            )
                        }
                    )
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.list_not_found),
                        modifier = Modifier
                    )
                    Text(
                        text = stringResource(R.string.not_content),
                        modifier = Modifier
                    )
                    IconButton(onClick = { viewModel.onEvent(OnRefreshPage) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "")
                    }
                }
            }
        }
    }
}
