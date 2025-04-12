package org.af.currencyapp.domain.repository

import kotlinx.coroutines.flow.Flow
import org.af.currencyapp.domain.model.Currency
import org.af.currencyapp.util.CurrencyCode

interface PreferencesRepo {
    fun saveLastTimeStamp(lastUpdated:String)
    suspend fun isDateFresh(currencyTimeStamp:Long): Boolean
    suspend fun saveTargetCurrency(code:String)
    suspend fun saveSourceCurrency(code:String)
    fun readTargetCurrency(): Flow<CurrencyCode>
    fun readSourceCurrency(): Flow<CurrencyCode>
}