@file:OptIn(ExperimentalResourceApi::class)

package org.af.currencyapp.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.exchange_illustration
import currencyapp.composeapp.generated.resources.refresh_ic
import currencyapp.composeapp.generated.resources.switch_ic
import headerColor
import org.af.currencyapp.data.remote.api.RequestState
import org.af.currencyapp.domain.model.Currency
import org.af.currencyapp.domain.model.CurrencyType
import org.af.currencyapp.domain.model.RateStates
import org.af.currencyapp.util.CurrencyCode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import staleColor
import util.displayCurrentDateTime

@Composable
fun HomeHeader(
    status: RateStates,
    onRateChange: () -> Unit,
    onSwitch: () -> Unit,
    amount: Double,
    onAmountChange: (Double) -> Unit,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onCurrencyTypeSelected: (CurrencyType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
            .background(headerColor)
            .padding(all = 24.dp)
    ){
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(
            status = status,
            onRateChange = onRateChange
        )
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInputs(
            source = source,
            target = target,
            onSwitch = onSwitch,
            onCurrencyTypeSelected = onCurrencyTypeSelected
        )
        AmountInput(onAmountChange = onAmountChange, amount = amount)
    }
}

@Composable
private fun CurrencyInputs(
    source:RequestState<Currency>,
    target:RequestState<Currency>,
    onSwitch:()->Unit,
    onCurrencyTypeSelected: (CurrencyType) -> Unit
){
    var animationStarted by remember { mutableStateOf(false) }
    val animateRotation by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = androidx.compose.animation.core.tween(300)
    )

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CurrencyView(
            placeholder = "From",
            currency = source,
            onClick = {
                if (source.isSuccess()) {
                    onCurrencyTypeSelected(
                        CurrencyType.Source(
                            currencyCode = CurrencyCode.valueOf(
                                source.getSuccessData().code
                            )
                        )
                    )
                }
            }
        )
        IconButton(
            modifier = Modifier.padding(24.dp).graphicsLayer {
                rotationY = animateRotation
            },
            onClick = {
                animationStarted = !animationStarted
                onSwitch()
            }) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Swap",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        CurrencyView(
            placeholder = "To",
            currency = target,
           onClick =  {
                if (target.isSuccess()) {
                    onCurrencyTypeSelected(
                        CurrencyType.Target(
                            currencyCode = CurrencyCode.valueOf(
                                target.getSuccessData().code
                            )
                        )
                    )
                }
            }        )
    }
}

@Composable
fun RatesStatus(
    status: RateStates,
    onRateChange: () -> Unit
) {
   Row(
       modifier = Modifier.fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceAround
   ) {
       Image(
           modifier = Modifier.size(50.dp),
           painter = painterResource(Res.drawable.exchange_illustration),
           contentDescription = "Exchange illustration"
       )
       Spacer(modifier = Modifier.width(24.dp))
       Column {
           Text(displayCurrentDateTime(), color = Color.White)
           Spacer(modifier = Modifier.height(8.dp))
           Text(
               status.title,
               color = status.color,
               fontSize = MaterialTheme.typography.bodySmall.fontSize
           )
       }
       if(status == RateStates.Stale) {
           IconButton(onClick = onRateChange){
               Icon(
                   modifier = Modifier.size(24.dp),
                   painter = painterResource(Res.drawable.refresh_ic),
                   contentDescription = "Refresh",
                   tint = staleColor
               )
           }
       }
   }

}

@Composable
private fun RowScope.CurrencyView(
    placeholder:String,
    currency:RequestState<Currency>,
    onClick:()->Unit
){

    Column(
        modifier = Modifier.weight(1f)
    ) {
        Text(
            modifier = Modifier.padding(start = 13.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.0f))
                .height(54.dp)
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(currency.isSuccess()){
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(CurrencyCode.valueOf(currency.getSuccessData().code).flag),
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = CurrencyCode.valueOf(currency.getSuccessData().code).name,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }

}

@Composable
fun AmountInput(
    amount:Double,
    onAmountChange:(Double)->Unit
){
    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = {
            onAmountChange(it.toDouble())
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.5f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = "0.0",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold)
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Decimal
        )
    )
}