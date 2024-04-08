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
import androidx.compose.runtime.DisposableEffect
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
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.domain.dto.ShoppingItemCreateDTO
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListViewModel


@Composable
fun ShoppingItemUpsertScreen(
    onDismissRequest: () -> Unit,
    title: String,
    defaultValue: ShoppingItem = ShoppingItem(),
    confirmEvent: (ShoppingItem) -> Unit,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    DisposableEffect(key1 = Unit) {
        viewModel.itemName = defaultValue.itemName
        viewModel.price = defaultValue.price.toString()
        viewModel.amount = defaultValue.amount.toString()

        onDispose {
            viewModel.itemName = ""
            viewModel.price = ""
            viewModel.amount = ""
        }
    }
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
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.item_name)) },
                    value = viewModel.itemName,
                    onValueChange = { viewModel.itemName = it })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.item_amount)) },
                    value = viewModel.amount.toString(),
                    onValueChange = { viewModel.amount = it })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.unit_price)) },
                    value = viewModel.price,
                    onValueChange = { viewModel.price = it })

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
                        defaultValue.itemName = viewModel.itemName
                        defaultValue.amount = viewModel.amount.toInt()
                        defaultValue.price = viewModel.price.toFloat()

                        confirmEvent(defaultValue)
                        onDismissRequest()
                    }) {
                        Text(text = stringResource(R.string.btn_save))
                    }
                }
            }
        }
    }
}

