package com.example.spendingtrackerapp.my_balance.presentation

sealed interface MyBalanceAction {

    data class OnBalanceChanged(val newBalance : Double) : MyBalanceAction
    data object OnSaveBalance:MyBalanceAction
}