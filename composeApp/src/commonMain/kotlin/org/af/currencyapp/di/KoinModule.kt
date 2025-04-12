package org.af.currencyapp.di

import com.russhwolf.settings.Settings
import org.af.currencyapp.data.local.PreferenceRepoImpl
import org.af.currencyapp.data.repository_impl.CurrencyRepoImpl
import org.af.currencyapp.data.repository_impl.MongoRepoImpl
import org.af.currencyapp.domain.repository.CurrencyRepo
import org.af.currencyapp.domain.repository.MongoRepo
import org.af.currencyapp.domain.repository.PreferencesRepo
import org.af.currencyapp.ui.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


val appModule = module {
    single { Settings() }
    single<MongoRepo> { MongoRepoImpl() }
    single<PreferencesRepo> {PreferenceRepoImpl(settings = get())}
    single<CurrencyRepo> { CurrencyRepoImpl(preferencesRepo = get()) }
    factory { HomeViewModel(
        preferencesRepo = get(),
        currencyRepo = get(),
        mongoRepo = get()
    )
    }
}

fun initKoin(){
    startKoin{
        modules(appModule)
    }
}