package com.example.spendingtrackerapp.inser_spending.presentation

sealed interface SpendingEvent {
    data object SaveSuccess:SpendingEvent
    data object SaveFailed:SpendingEvent
}