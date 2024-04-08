package callofproject.dev.shoppinglistapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.mainpage.components.CreateListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(title: String = stringResource(R.string.default_title)) {

    val menuItems = listOf(stringResource(R.string.create_list_menu))
    val expandedMenu = remember { mutableStateOf(false) }
    val createListScreenExpanded = remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier
            .border(1.dp, Color.Black),
        title = {
            if (createListScreenExpanded.value)
                CreateListScreen(onDismissRequest = { createListScreenExpanded.value = false })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600)
                )
                IconButton(onClick = {
                    expandedMenu.value = true

                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "menu"
                    )
                    DropdownMenu(
                        expanded = expandedMenu.value,
                        onDismissRequest = { expandedMenu.value = false },

                        ) {
                        menuItems.forEachIndexed { _, item ->
                            DropdownMenuItem(text = { Text(text = item) }, onClick = {
                                createListScreenExpanded.value = true
                                expandedMenu.value = false
                            })
                        }
                    }
                }
            }
        },
        windowInsets = WindowInsets(right = 10.dp)
    )
}