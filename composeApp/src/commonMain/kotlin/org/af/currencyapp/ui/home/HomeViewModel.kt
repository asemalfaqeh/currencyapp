package org.af.currencyapp.ui.home


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.af.currencyapp.data.remote.api.RequestState
import org.af.currencyapp.domain.model.Currency
import org.af.currencyapp.domain.model.RateStates
import org.af.currencyapp.domain.repository.CurrencyRepo
import org.af.currencyapp.domain.repository.MongoRepo
import org.af.currencyapp.domain.repository.PreferencesRepo

class HomeViewModel(
    private val preferencesRepo: PreferencesRepo,
    private val currencyRepo: CurrencyRepo,
    private val mongoRepo: MongoRepo,
    ) : ScreenModel {

    private var _state: MutableState<RateStates> = mutableStateOf(RateStates.Idle)
    val rateStatus: State<RateStates> = _state

    private var _target: MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    var target: State<RequestState<Currency>> = _target

    private var _source:MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    var source:State<RequestState<Currency>> = _source

    private var _allCurrencies = mutableStateListOf<Currency>()
    var allCurrencies:List<Currency> = _allCurrencies

    init {
        screenModelScope.launch {
            getLastExchangeRates()
            getRateStatus()
            readTargetCurrencyCode()
            readSourceCurrencyCode()
        }
    }

    fun sendEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.RefreshRates -> {
                screenModelScope.launch {
                    getLastExchangeRates()
                }
            }
            is HomeUiEvent.SaveSourceCurrencyCode -> {
                saveCurrencySourceCode(event.code)
            }
            is HomeUiEvent.SaveTargetCurrencyCode -> {
                saveCurrencyTargetCode(event.code)
            }
            HomeUiEvent.SwitchCurrencies ->{
                switchCurrencies()
            }
        }
    }

    private fun switchCurrencies() {
        val target = _target.value
        val source = _source.value
        _target.value = source
        _source.value = target
    }

    private suspend fun getLastExchangeRates() {
        try {
            val localCache = mongoRepo.readCurrencyData().first()
            if (localCache.isSuccess()){
               if(localCache.getSuccessData().isNotEmpty()){
                   println("DataBase is FULL")
                   if(!preferencesRepo.isDateFresh(Clock.System.now().toEpochMilliseconds())){
                       println("DataBase is Not Fresh")
                       cachedData()
                   }else{
                       println("DataBase is Fresh")
                       _allCurrencies.addAll(localCache.getSuccessData())
                   }
               }else{
                   println("DataBase is EMPTY")
                   cachedData()
               }
            }else{
                println("Something went wrong ${localCache.getError().message}")
            }

        } catch (e: Exception) {
            print("${e.message}")
        }
    }

    private fun readTargetCurrencyCode(){
        screenModelScope.launch(Dispatchers.Main) {
            preferencesRepo.readTargetCurrency().collectLatest{
                val selectedCurrency = _allCurrencies.find { currency ->
                    currency.code == it.name
                }
               if (selectedCurrency != null){
                   _source.value = RequestState.Success(selectedCurrency)
               }else{
                   _source.value = RequestState.Error(message = "Count Not Found")
               }
            }
        }
    }

    private fun readSourceCurrencyCode() {
        screenModelScope.launch(Dispatchers.Main) {
            preferencesRepo.readSourceCurrency().collectLatest {
                val selectedCurrency = _allCurrencies.find { currency ->
                    currency.code == it.name
                }
                if (selectedCurrency != null) {
                    _target.value = RequestState.Success(selectedCurrency)
                } else {
                    _target.value = RequestState.Error(message = "Count Not Found")
                }
            }
        }
    }

    private suspend fun cachedData(){
        val data = currencyRepo.getLastExchangeRates()
        if(data.isSuccess()){
            mongoRepo.cleanUp()
            data.getSuccessData().forEach { currency->
                println("Insert into MongoDB ${currency.code}")
                mongoRepo.insertCurrencyData(currency)
            }
            println("DataBase is Fresh2")
            _allCurrencies.clear()
            _allCurrencies.addAll(data.getSuccessData())
        }else{
            println("Something went wrong ${data.getError().message}")
        }
    }

    private fun saveCurrencyTargetCode(code:String){
        screenModelScope.launch(Dispatchers.IO) {
            preferencesRepo.saveTargetCurrency(code)
        }
    }

    private fun saveCurrencySourceCode(code:String){
        screenModelScope.launch(Dispatchers.IO) {
            preferencesRepo.saveSourceCurrency(code)
        }
    }

    private suspend fun getRateStatus() {
        _state.value = if (preferencesRepo.isDateFresh(Clock.System.now().toEpochMilliseconds()))
            RateStates.Fresh
        else
            RateStates.Stale
    }
}