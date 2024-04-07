package callofproject.dev.shoppinglistapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SummaryDisplay(title: String, price: String) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,

            textAlign = TextAlign.End
        )
        Text(
            text = price,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
        )
    }
}