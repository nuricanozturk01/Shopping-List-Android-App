package callofproject.dev.shoppinglistapp.presentation.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import callofproject.dev.shoppinglistapp.R


@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onConfirmation();onDismissRequest() }) {
                Text(stringResource(R.string.btn_remove))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(R.string.btn_cancel))
            }
        }
    )
}
