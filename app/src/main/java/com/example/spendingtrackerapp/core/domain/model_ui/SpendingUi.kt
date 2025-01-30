package com.example.spendingtrackerapp.core.domain.model_ui

import java.time.ZonedDateTime

data class SpendingUi(
    val spendingId :Int?,
    val name : String,
    val price : Double,
    val kilograms : Double,
    val quantity : Double,
    val dateTime : ZonedDateTime,
    val imageUrl : String,
    val color : Int =0

)
