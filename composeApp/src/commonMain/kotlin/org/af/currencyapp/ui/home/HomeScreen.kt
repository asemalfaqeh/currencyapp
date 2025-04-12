package org.af.currencyapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.af.currencyapp.domain.model.CurrencyType
import org.af.currencyapp.ui.component.CurrencyPickerDialog
import org.af.currencyapp.ui.component.HomeBody
import org.af.currencyapp.ui.component.HomeHeader
import surfaceColor

class HomeScreen : Screen{


    @Composable
    override fun Content() {

        val homeViewModel = getScreenModel<HomeViewModel>()
        val status by homeViewModel.rateStatus
        val targetCurrency by homeViewModel.target
        val sourceCurrency by homeViewModel.source

        val allCurrencies = homeViewModel.allCurrencies

        var amount by rememberSaveable{ mutableStateOf(0.0) }

        var dialogOpened by remember { mutableStateOf(true) }
        var selectedCurrencyType:CurrencyType by remember {
            mutableStateOf(CurrencyType.None)
        }

        if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
            CurrencyPickerDialog(
                currencies = allCurrencies,
                currencyType = selectedCurrencyType,
                onConfirmClick = { currencyCode ->
                    if (selectedCurrencyType is CurrencyType.Source) {
                        homeViewModel.sendEvent(
                            HomeUiEvent.SaveSourceCurrencyCode(
                                code = currencyCode.name
                            )
                        )
                    } else if (selectedCurrencyType is CurrencyType.Target) {
                        homeViewModel.sendEvent(
                            HomeUiEvent.SaveTargetCurrencyCode(
                                code = currencyCode.name
                            )
                        )
                    }
                    dialogOpened = false
                },
                onDismiss = {
                    selectedCurrencyType = CurrencyType.None
                    dialogOpened = false
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .background(surfaceColor)
        ) {
            HomeHeader(
                onRateChange = {
                    homeViewModel.sendEvent(HomeUiEvent.RefreshRates)
                },
                status = status,
                amount = amount,
                onAmountChange = {
                    amount = it
                },
                onSwitch = {
                    homeViewModel.sendEvent(HomeUiEvent.SwitchCurrencies)
                },
                source = sourceCurrency,
                target = targetCurrency,
                onCurrencyTypeSelected = {
                    dialogOpened = true
                    selectedCurrencyType = it
                }
            )
            HomeBody(
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount
            )
            Spacer(modifier = Modifier.weight(1f).height(18.dp))
        }
    }
}