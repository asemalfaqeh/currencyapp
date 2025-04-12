package org.af.currencyapp.ui.home

sealed class HomeUiEvent {
    data object RefreshRates : HomeUiEvent()
    data object SwitchCurrencies: HomeUiEvent()
    data class SaveSourceCurrencyCode(val code: String): HomeUiEvent()
    data class SaveTargetCurrencyCode(val code: String): HomeUiEvent()
}