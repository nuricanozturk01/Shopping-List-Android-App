package callofproject.dev.shoppinglistapp.presentation.shopping_list.shopping_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.entity.ShoppingItem
import callofproject.dev.shoppinglistapp.presentation.components.SummaryDisplay
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListEvent
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListViewModel


@Composable
fun ShoppingItemScreen(
    influxMoney: String,
    onDeleteClick: () -> Unit,
    item: ShoppingItem,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    var expandedEditModal by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .heightIn(min = 70.dp)
            .shadow(1.dp)
            .clip(RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.itemName,
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis
            )

            SummaryDisplay(
                title = "${stringResource(R.string.price)}($influxMoney)",
                price = item.price.toString()
            )
            SummaryDisplay(
                title = stringResource(R.string.item_amount),
                price = item.amount.toString()
            )
            SummaryDisplay(
                title = "${stringResource(R.string.total_price)}($influxMoney)",
                price = (item.price * item.amount).toString()
            )
        }

        Icon(
            Icons.Filled.Edit,
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clickable { expandedEditModal = true }
        )
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clickable { onDeleteClick() }
        )

        if (expandedEditModal) {
            ShoppingItemUpsertScreen(
                title = stringResource(R.string.edit_item_menu),
                defaultValue = item,
                onDismissRequest = { expandedEditModal = false },
                confirmEvent = {
                    viewModel.onEvent(ShoppingListEvent.OnEditShoppingItem(item))
                },
            )
        }
    }
}
