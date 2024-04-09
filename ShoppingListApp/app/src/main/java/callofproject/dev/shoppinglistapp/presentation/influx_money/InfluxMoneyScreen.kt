package callofproject.dev.shoppinglistapp.presentation.influx_money

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import callofproject.dev.shoppinglistapp.R
import callofproject.dev.shoppinglistapp.domain.dto.InfluxType
import callofproject.dev.shoppinglistapp.presentation.components.ActionButton
import callofproject.dev.shoppinglistapp.presentation.components.SelectableButton
import callofproject.dev.shoppinglistapp.util.route.UiEvent

@Composable
fun InfluxMoneyScreen(
    onNextClick: () -> Unit,
    viewModel: InfluxMoneyViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.influx_money),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.money_tl),
                    isSelected = viewModel.selectedInfluxMoney is InfluxType.MoneyTL,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onInfluxSelect(InfluxType.MoneyTL)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                SelectableButton(
                    text = stringResource(id = R.string.money_dollar),
                    isSelected = viewModel.selectedInfluxMoney is InfluxType.MoneyUSD,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onInfluxSelect(InfluxType.MoneyUSD)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                SelectableButton(
                    text = stringResource(id = R.string.money_euro),
                    isSelected = viewModel.selectedInfluxMoney is InfluxType.MoneyEUR,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onInfluxSelect(InfluxType.MoneyEUR)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}