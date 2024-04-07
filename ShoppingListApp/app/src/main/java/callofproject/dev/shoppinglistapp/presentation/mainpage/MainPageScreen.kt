package callofproject.dev.shoppinglistapp.presentation.mainpage


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.components.AlertDialogExample
import callofproject.dev.shoppinglistapp.presentation.components.ShoppingListItem
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.route.UiEvent

@Composable
fun MainPageScreen(
    viewModel: MainPageViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
    onItemsClick: () -> Unit
) {

    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent()
        LazyColumn(
            //modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (state.shoppingLists.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {

            if (state.shoppingLists.isNotEmpty()) {
                itemsIndexed(state.shoppingLists) { _, item ->
                    ShoppingListItem(
                        listName = item.listName,
                        creationTime = viewModel.toDateTimeStr(item.creationTime),
                        itemCount = "${item.itemCount} ürün listelendi",
                        onEditClick = { },
                        onDeleteClick = { },
                        onItemsClick = onItemsClick
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
                }
            }
        }
    }
}
