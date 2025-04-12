package org.af.currencyapp.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CurrencyApiService {
    companion object{
        const val BASE_URL = "https://api.currencyapi.com/v3/"
        const val API_KEY = "cur_live_udOj1mzUHqKpgBIa3iyt2PGwctAyImlTgRh8C6ti"
    }
}

object ApiServiceClient {
    private var client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout){
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
        install(DefaultRequest){
            headers.append("apiKey", CurrencyApiService.API_KEY)
        }
    }

    fun httpClient() = client

}