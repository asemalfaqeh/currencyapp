package org.af.currencyapp


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.af.currencyapp.di.initKoin
import org.af.currencyapp.ui.home.HomeScreen

@Composable
@Preview
fun App() {

    initKoin()
    val colorScheme = if(isSystemInDarkTheme()) lightColorScheme() else darkColorScheme()
    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Navigator(HomeScreen())
    }
}