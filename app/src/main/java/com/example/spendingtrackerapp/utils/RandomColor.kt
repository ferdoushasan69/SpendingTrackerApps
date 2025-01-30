package com.example.spendingtrackerapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.random.Random

fun randomColor(minBrightness : Int=90): Int {
    val random = Random.Default
    val red = random.nextInt(minBrightness,233)
    val blue = random.nextInt(minBrightness,222)
    val green = random.nextInt(minBrightness,156)

    return Color(red,blue,green).copy(.5f).toArgb()
    
}