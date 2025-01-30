package com.example.spendingtrackerapp.home.presentation


sealed interface HomeAction {
    data object LoadingHomeViewBalance:HomeAction
    data class OnDateChanged(val newDate : Int) : HomeAction
    data class OnDeleteSpending(val spendingId : Int) : HomeAction
}