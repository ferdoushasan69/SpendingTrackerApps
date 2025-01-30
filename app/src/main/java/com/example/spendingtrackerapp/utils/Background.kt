package com.example.spendingtrackerapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun BackGround(

) {
   Box(modifier = Modifier.fillMaxSize()
       .background(color = MaterialTheme.colorScheme.background)
       .background(brush = Brush.verticalGradient(
           listOf(
               MaterialTheme.colorScheme.inverseOnSurface.copy(.5f),
               MaterialTheme.colorScheme.onSecondaryContainer
           )
       ))
   )
}