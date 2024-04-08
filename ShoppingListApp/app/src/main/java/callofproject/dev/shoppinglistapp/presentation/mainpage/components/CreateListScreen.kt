package callofproject.dev.shoppinglistapp.presentation.mainpage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import callofproject.dev.shoppinglistapp.R


@Composable
fun CreateListScreen(
    confirmEvent: (String) -> Unit,
    title: String,
    onDismissRequest: () -> Unit = {},
    defaultValue: String = ""
) {
    var listName by remember { mutableStateOf(defaultValue) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                OutlinedTextField(
                    label = { Text(text = "Liste Adı") },
                    value = listName,
                    onValueChange = { listName = it })


                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(onClick = { onDismissRequest() }) {
                        Text(text = stringResource(id = R.string.btn_cancel))
                    }

                    OutlinedButton(onClick = { confirmEvent(listName) }) {
                        Text(text = stringResource(id = R.string.btn_save))
                    }
                }
            }
        }
    }
}

