package com.example.spendingtrackerapp.utils

import androidx.room.Insert
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object MyBalance : Screen

    @Serializable
    data class InsertSpending(val spendingId : Int?=null) : Screen
}