package callofproject.dev.shoppinglistapp.presentation.mainpage.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.data.entity.ShoppingList
import callofproject.dev.shoppinglistapp.presentation.components.AlertDialog
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageEvent
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageViewModel
import callofproject.dev.shoppinglistapp.util.route.UiEvent


@Composable
fun ShoppingListItem(
    onItemsClick: () -> Unit,
    viewModel: MainPageViewModel = hiltViewModel(),
    shoppingListItem: ShoppingList
) {
    val editScreenExpanded = remember { mutableStateOf(false) }
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
            .clip(RoundedCornerShape(40))
            .padding(10.dp)
            .height(100.dp)
            .background(MaterialTheme.colorScheme.onSecondary)
            .shadow(.5.dp, clip = true, shape = RoundedCornerShape(1.dp)),
        verticalAlignment = Alignment.CenterVertically,
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
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    text = shoppingListItem.listName,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(2f),
                )

                Text(
                    text = viewModel.toDateTimeStr(shoppingListItem.creationTime),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "${shoppingListItem.itemCount} ${stringResource(R.string.x_listed_item)}",
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

        }
        IconButton(onClick = { editScreenExpanded.value = true }) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "",
                modifier = Modifier.size(35.dp)
            )
        }
        IconButton(onClick = {
            expandedAlertDialog = true
        }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "",
                modifier = Modifier.size(35.dp)
            )

        }

        if (expandedAlertDialog)
            AlertDialog(
                dialogTitle = stringResource(R.string.confirm_dialog_title),
                dialogText = stringResource(R.string.confirm_dialog_text),
                onConfirmation = {
                    viewModel.onEvent(
                        MainPageEvent.OnRemoveShoppingListClick(
                            shoppingListItem.listId
                        )
                    )
                },
                onDismissRequest = { expandedAlertDialog = false }
            )
        if (editScreenExpanded.value)
            CreateListScreen(
                defaultValue = shoppingListItem.listName,
                title = stringResource(R.string.edit_list),
                confirmEvent = {
                    viewModel.onEvent(MainPageEvent.OnEditShoppingListClick(shoppingListItem, it))
                },
                onDismissRequest = { editScreenExpanded.value = false })
    }
}
