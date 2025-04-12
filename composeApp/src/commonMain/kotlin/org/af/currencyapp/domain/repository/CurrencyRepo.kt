package org.af.currencyapp.domain.repository

import org.af.currencyapp.data.remote.api.RequestState
import org.af.currencyapp.domain.model.Currency

interface CurrencyRepo {
    suspend fun getLastExchangeRates(): RequestState<List<Currency>>
}