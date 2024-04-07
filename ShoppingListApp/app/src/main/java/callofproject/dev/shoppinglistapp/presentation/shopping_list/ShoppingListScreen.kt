package callofproject.dev.shoppinglistapp.presentation.shopping_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import callofproject.dev.shoppinglistapp.presentation.components.AlertDialogExample
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.presentation.shopping_item.ShoppingItemScreen

@Composable
fun ShoppingListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent()
        //ShoppingItemScreen(itemName = "Total")
        //Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(17) {
                ShoppingItemScreen()
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewShoppingListComponent() {
    ShoppingListScreen()
}