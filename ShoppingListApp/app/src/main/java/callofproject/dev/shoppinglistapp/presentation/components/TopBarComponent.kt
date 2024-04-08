package callofproject.dev.shoppinglistapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.presentation.mainpage.components.CreateListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    confirmEvent: (String) -> Unit,
    title: String = stringResource(R.string.default_title)
) {


    val expandedMenu = remember { mutableStateOf(false) }
    val createListScreenExpanded = remember { mutableStateOf(false) }
    val changeLangScreenExpanded = remember { mutableStateOf(false) }
    val menuItems = listOf(
        Pair(stringResource(R.string.create_list_menu)) { createListScreenExpanded.value = true },
        Pair(stringResource(R.string.change_lang)) { changeLangScreenExpanded.value = true }
    )
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            navigationIconContentColor = Color.Transparent,
            actionIconContentColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.clip(RoundedCornerShape(20)),
        title = {
            if (createListScreenExpanded.value)
                CreateListScreen(
                    confirmEvent = { confirmEvent(it) },
                    title = stringResource(R.string.create_list_menu),
                    onDismissRequest = { createListScreenExpanded.value = false },
                )


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
                            DropdownMenuItem(text = { Text(text = item.first) }, onClick = {
                                item.second()
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

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBarComponent(confirmEvent = {})
}