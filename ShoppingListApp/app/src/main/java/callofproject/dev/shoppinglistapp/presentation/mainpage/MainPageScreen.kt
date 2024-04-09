package callofproject.dev.shoppinglistapp.presentation.mainpage


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.presentation.mainpage.components.ShoppingListItem
import callofproject.dev.shoppinglistapp.util.route.UiEvent

@Composable
fun MainPageScreen(
    onNavigateToItems: (Long) -> Unit,
    viewModel: MainPageViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
) {

    val state = viewModel.state.value
    val context = LocalContext.current

    DisposableEffect(key1 = Unit) {
        viewModel.findAll()
        onDispose { }
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
        TopBarComponent(confirmEvent = { viewModel.onEvent(MainPageEvent.OnClickSaveListBtn(it)) })
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (state.shoppingLists.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {

            if (state.shoppingLists.isNotEmpty()) {
                items(state.shoppingLists.size) { idx ->
                    ShoppingListItem(
                        shoppingListItem = state.shoppingLists[idx],
                        onItemsClick = { onNavigateToItems(state.shoppingLists[idx].listId) }
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
                    IconButton(onClick = { viewModel.onEvent(MainPageEvent.OnRefreshPage) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "")
                    }
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
