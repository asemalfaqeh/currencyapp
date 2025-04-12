package org.af.currencyapp.data.repository_impl

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import org.af.currencyapp.data.remote.api.ApiServiceClient
import org.af.currencyapp.data.remote.api.CurrencyApiService.Companion.BASE_URL
import org.af.currencyapp.data.remote.api.RequestState
import org.af.currencyapp.domain.model.Currency
import org.af.currencyapp.domain.model.LatestResponse
import org.af.currencyapp.domain.repository.CurrencyRepo
import org.af.currencyapp.domain.repository.PreferencesRepo
import org.af.currencyapp.util.CurrencyCode

class CurrencyRepoImpl(
    private val preferencesRepo: PreferencesRepo
) : CurrencyRepo {

    override suspend fun getLastExchangeRates(): RequestState<List<Currency>> {
        return  try {
            val response = ApiServiceClient.httpClient().get("$BASE_URL/latest")
            if (response.status.value == 200) {
                println(response.body<String>())
                val data = Json.decodeFromString<LatestResponse>(response.body())

                val availableCurrencies = data.data.keys.filter {
                    CurrencyCode.entries.map {
                        code -> code.name
                    }.toSet().contains(it)
                }

                val currencies = data.data.values.filter { code ->
                    availableCurrencies.contains(code.code)
                }

                /// Save Last Date Updated
                val lastUpdatedDate = data.meta.lastUpdatedAt
                preferencesRepo.saveLastTimeStamp(lastUpdatedDate)
                RequestState.Success(data = currencies)
            } else {
                RequestState.Error( "Something went wrong ${response.status}")
            }
        } catch (e:Exception){
           return RequestState.Error(message = e.message ?: "Something went wrong")
        }
    }

}