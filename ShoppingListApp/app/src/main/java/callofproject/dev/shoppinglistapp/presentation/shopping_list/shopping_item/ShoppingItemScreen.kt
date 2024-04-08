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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.components.SummaryDisplay


@Composable
fun ShoppingItemScreen(
    itemName: String,
    unitPrice: String,
    amount: String,
    totalPrice: String,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {

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
                text = itemName,
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis
            )

            SummaryDisplay(title = stringResource(R.string.price), price = unitPrice)
            SummaryDisplay(title = stringResource(R.string.item_amount), price = amount)
            SummaryDisplay(title = stringResource(R.string.total_price), price = totalPrice)
        }

        Icon(
            Icons.Filled.Edit,
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clickable { onEditClick() }
        )
        Icon(
            Icons.Filled.Delete,
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clickable { onDeleteClick() }
        )
    }
}
