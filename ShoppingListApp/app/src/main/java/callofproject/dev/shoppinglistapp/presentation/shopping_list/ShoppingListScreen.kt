package callofproject.dev.shoppinglistapp.presentation.shopping_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.components.TopBarComponent
import callofproject.dev.shoppinglistapp.presentation.shopping_item.MinimalDialog
import callofproject.dev.shoppinglistapp.presentation.shopping_item.ShoppingItemScreen

@Composable
fun ShoppingListScreen() {
    var expandedCreateModal by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .heightIn(min = 50.dp)
                .shadow(1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Total Price:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight(700)
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "${stringResource(R.string.money_dollar)}500",
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.width(130.dp))
            ElevatedButton(
                onClick = {
                    expandedCreateModal = true
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }

        if (expandedCreateModal)
            MinimalDialog(onDismissRequest = {
                expandedCreateModal = false
            })

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