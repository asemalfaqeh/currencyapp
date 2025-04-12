package org.af.currencyapp.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.af.currencyapp.domain.model.Currency
import org.af.currencyapp.domain.repository.PreferencesRepo
import org.af.currencyapp.util.CurrencyCode

@OptIn(ExperimentalSettingsApi::class)
class PreferenceRepoImpl(
    private val settings:Settings
)  : PreferencesRepo{

    companion object {
        const val TIMESTAMP_KEY = "lastUpdated"
        const val TARGET_CURRENCY_KEY = "targetCurrency"
        const val SOURCE_CURRENCY_KEY = "sourceCurrency"
        val DEFAULT_TARGET_CURRENCY_CODE = CurrencyCode.USD.name
        val DEFAULT_SOURCE_CURRENCY = CurrencyCode.EUR.name
    }

    private val flowSettings:FlowSettings = (settings as ObservableSettings).toFlowSettings()

    override fun saveLastTimeStamp(lastUpdated: String) {
        settings.putLong(TIMESTAMP_KEY, Instant.parse(lastUpdated).toEpochMilliseconds())
    }

    override suspend fun isDateFresh(currencyTimeStamp: Long): Boolean {
        val savedTime = settings.getLong(TIMESTAMP_KEY, defaultValue = 0L)
        return if(savedTime != 0L){
            val savedInstant = Instant.fromEpochMilliseconds(savedTime)
            val currentInstant = Instant.fromEpochMilliseconds(currencyTimeStamp)
            val currencyDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val differenceTime = currencyDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
            differenceTime < 1
        } else false
    }

    override suspend fun saveTargetCurrency(code: String) {
       flowSettings.putString(TARGET_CURRENCY_KEY, code)
    }

    override suspend fun saveSourceCurrency(code: String) {
        settings.putString(SOURCE_CURRENCY_KEY, code)
    }

    override fun readTargetCurrency(): Flow<CurrencyCode> {
       return flowSettings.getStringFlow(TARGET_CURRENCY_KEY, DEFAULT_TARGET_CURRENCY_CODE).map {
           CurrencyCode.valueOf(it)
       }
    }

    override fun readSourceCurrency(): Flow<CurrencyCode> {
       return flowSettings.getStringFlow(SOURCE_CURRENCY_KEY, DEFAULT_SOURCE_CURRENCY).map {
           CurrencyCode.valueOf(it)
       }
    }
}