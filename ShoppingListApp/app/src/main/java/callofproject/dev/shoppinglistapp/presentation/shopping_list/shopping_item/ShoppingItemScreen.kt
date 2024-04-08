package callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.presentation.components.AlertDialog
import callofproject.dev.shoppinglistapp.presentation.components.SummaryDisplay
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent.OnEditShoppingItem
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListViewModel
import callofproject.dev.shoppinglistapp.route.UiEvent


@Composable
fun ShoppingItemScreen(
    influxMoney: String,
    onDeleteClick: () -> Unit,
    item: ShoppingItem,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    var expandedEditModal by remember { mutableStateOf(false) }
    var expandedAlertDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(45))
            .padding(10.dp)
            .height(100.dp)
            .background(MaterialTheme.colorScheme.onSecondary)
            .shadow(.5.dp, clip = true, shape = RoundedCornerShape(1.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.itemName,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(10.dp))
            SummaryDisplay(
                title = "${stringResource(R.string.price)}($influxMoney)",
                price = item.price.toString()
            )
            Spacer(modifier = Modifier.width(10.dp))
            SummaryDisplay(
                title = stringResource(R.string.item_amount),
                price = item.amount.toString()
            )
            Spacer(modifier = Modifier.width(10.dp))
            SummaryDisplay(
                title = "${stringResource(R.string.total_price)}($influxMoney)",
                price = (item.price * item.amount).toString()
            )

            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.Filled.Edit,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { expandedEditModal = true }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.Filled.Delete,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { expandedAlertDialog = true }
            )
        }

        if (expandedAlertDialog)
            AlertDialog(
                dialogTitle = stringResource(R.string.confirm_dialog_title),
                dialogText = stringResource(R.string.confirm_dialog_text),
                onConfirmation = onDeleteClick,
                onDismissRequest = { expandedAlertDialog = false }
            )

        if (expandedEditModal) {
            ShoppingItemUpsertScreen(
                itemNameStr = item.itemName,
                amountStr = item.amount.toString(),
                priceStr = item.price.toString(),
                title = stringResource(R.string.edit_item_menu),
                onDismissRequest = { expandedEditModal = false },
                confirmEvent = { name, amount, price ->
                    viewModel.onEvent(OnEditShoppingItem(item, name, amount, price))
                },
            )
        }
    }
}
