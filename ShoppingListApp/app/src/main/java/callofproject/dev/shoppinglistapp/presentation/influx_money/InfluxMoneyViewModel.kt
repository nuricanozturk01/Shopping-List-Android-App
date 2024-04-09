package callofproject.dev.shoppinglistapp.presentation.influx_money

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import callofproject.dev.shoppinglistapp.domain.dto.InfluxType
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import callofproject.dev.shoppinglistapp.util.route.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfluxMoneyViewModel @Inject constructor(private val preferences: IPreferences) :
    ViewModel() {

    var selectedInfluxMoney by mutableStateOf<InfluxType>(InfluxType.MoneyTL)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onInfluxSelect(influxType: InfluxType) {
        selectedInfluxMoney = influxType
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveInfluxMoney(selectedInfluxMoney)
            _uiEvent.send(UiEvent.Success)
        }
    }
}