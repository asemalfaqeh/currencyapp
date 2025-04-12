package org.af.currencyapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorScreen(
    message:String?,
    modifier: Modifier = Modifier
){
   Box(
       modifier = modifier.fillMaxSize(),
       contentAlignment = Alignment.Center
   ){
       Text(
           text = message ?: "Something went wrong",
           modifier = Modifier.align(Alignment.Center),
           textAlign = TextAlign.Center
       )
   }
}