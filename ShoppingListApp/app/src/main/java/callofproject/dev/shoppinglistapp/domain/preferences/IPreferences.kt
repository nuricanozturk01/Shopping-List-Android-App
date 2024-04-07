package callofproject.dev.shoppinglistapp.domain.preferences

import callofproject.dev.shoppinglistapp.domain.dto.InfluxType

interface IPreferences {

    fun saveInfluxMoney(influxType: InfluxType)

    fun getInfluxMoney(): String?

    companion object {
        const val KEY_INFLUX_MONEY = "influx_money"
    }
}