package callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListViewModel


@Composable
fun ShoppingItemCreateScreen(
    onDismissRequest: () -> Unit,
    listId: Long,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    var itemName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_item_menu),
                    style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.item_name)) },
                    value = itemName,
                    onValueChange = { itemName = it })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.item_amount)) },
                    value = amount,
                    onValueChange = { amount = it })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.unit_price)) },
                    value = price,
                    onValueChange = { price = it })

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(onClick = { onDismissRequest() }) {
                        Text(text = stringResource(R.string.btn_cancel))
                    }

                    OutlinedButton(onClick = {
                        viewModel.onEvent(
                            ShoppingListEvent.OnCreateItemClick(
                                ShoppingItemCreateDTO(
                                    itemName,
                                    price.toFloat(),
                                    amount.toInt(),
                                    listId = listId
                                )
                            )
                        )
                        onDismissRequest()
                    }) {
                        Text(text = stringResource(R.string.btn_save))
                    }
                }
            }
        }
    }
}

