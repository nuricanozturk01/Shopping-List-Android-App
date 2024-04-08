package callofproject.dev.shoppinglistapp.presentation.shopping_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent.OnRefreshPage
import callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item.ShoppingItemCreateScreen
import callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item.ShoppingItemScreen

@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel = hiltViewModel(), shoppingListId: Long) {
    viewModel.setListId(shoppingListId)
    var expandedCreateModal by remember { mutableStateOf(false) }

    val state = viewModel.state.value
    val context = LocalContext.current


    DisposableEffect(key1 = true) {
        viewModel.findAll(shoppingListId)
        onDispose { }
    }
    LaunchedEffect(key1 = true) {

        Toast.makeText(context, state.listId.toString(), Toast.LENGTH_LONG).show()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .heightIn(min = 50.dp)
                .shadow(1.dp),
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
                text = "${viewModel.influxMoney.value}${viewModel.totalPrice.value}",
                style = MaterialTheme.typography.bodyLarge,
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

        if (expandedCreateModal)
            ShoppingItemCreateScreen(onDismissRequest = {
                expandedCreateModal = false
            }, listId = shoppingListId)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (state.shoppingItemList.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {
            if (state.shoppingItemList.isNotEmpty()) {

                items(state.shoppingItemList.size) { idx ->
                    ShoppingItemScreen(
                        itemName = state.shoppingItemList[idx].itemName,
                        unitPrice = state.shoppingItemList[idx].price.toString(),
                        amount = state.shoppingItemList[idx].amount.toString(),
                        totalPrice = viewModel.evaluate(
                            state.shoppingItemList[idx].price,
                            state.shoppingItemList[idx].amount
                        ),
                        onDeleteClick = {
                            viewModel.onEvent(
                                ShoppingListEvent.OnRemoveItemClick(
                                    state.shoppingItemList[idx].itemId
                                )
                            )
                        },
                        onEditClick = {}
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
