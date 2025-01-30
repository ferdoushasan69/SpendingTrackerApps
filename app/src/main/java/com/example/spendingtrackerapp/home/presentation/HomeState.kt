package com.example.spendingtrackerapp.home.presentation

import android.os.Build
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import java.time.ZonedDateTime

data class HomeState(
    val spending : List<SpendingUi> = emptyList(),
    val dateList  : List<ZonedDateTime> = emptyList(),
    val balance : Double = 0.0,
    val pickDate : ZonedDateTime  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        ZonedDateTime.now()
    }else{
       throw IllegalStateException("wrong time")
    },
    val isDropDownMenuVisible :  Boolean = false
)
