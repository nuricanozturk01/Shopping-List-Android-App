package callofproject.dev.shoppinglistapp.data.preferences

import android.content.SharedPreferences
import callofproject.dev.shoppinglistapp.domain.dto.InfluxType
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import javax.inject.Inject

class DefaultPreferences @Inject constructor(private val preferences: SharedPreferences) : IPreferences {

    override fun saveInfluxMoney(influxType: InfluxType) {
        preferences.edit()
            .putString(IPreferences.KEY_INFLUX_MONEY, influxType.name)
            .apply()
    }

    override fun getInfluxMoney(): String? {
        return preferences.getString(IPreferences.KEY_INFLUX_MONEY, null)
    }
}