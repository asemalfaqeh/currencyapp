package org.af.currencyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform