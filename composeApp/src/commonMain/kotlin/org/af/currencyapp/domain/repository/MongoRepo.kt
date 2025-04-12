package org.af.currencyapp.domain.repository

import kotlinx.coroutines.flow.Flow
import org.af.currencyapp.data.remote.api.RequestState
import org.af.currencyapp.domain.model.Currency

interface MongoRepo {
    fun configureTheRealm()
    suspend fun insertCurrencyData(currency: Currency)
    fun readCurrencyData():Flow<RequestState<List<Currency>>>
    suspend fun cleanUp()
}