package org.af.currencyapp.domain.model

import androidx.compose.ui.graphics.Color
import freshColor
import staleColor

enum class RateStates(
    val title: String,
    val color: Color
){
    Idle(title = "Idle Rates", color = Color.White),
    Fresh(title = "Fresh Rates", color = freshColor),
    Stale(title = "Stale Rates", color = staleColor)
}