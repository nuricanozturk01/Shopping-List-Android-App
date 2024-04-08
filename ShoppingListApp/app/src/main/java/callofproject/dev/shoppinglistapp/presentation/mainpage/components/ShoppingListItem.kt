package callofproject.dev.shoppinglistapp.presentation.mainpage.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun ShoppingListItem(
    listName: String,
    creationTime: String,
    itemCount: String,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onItemsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(5.dp)
                .clickable { onItemsClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = listName,
                    style = MaterialTheme.typography.headlineSmall,
                )

                Text(
                    text = creationTime,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = itemCount,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        IconButton(onClick = onEditClick) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "",
                modifier = Modifier.size(35.dp)
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "",
                modifier = Modifier.size(35.dp)
            )

        }
    }
}
