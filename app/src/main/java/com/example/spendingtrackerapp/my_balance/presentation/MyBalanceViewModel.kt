package com.example.spendingtrackerapp.my_balance.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository
import kotlinx.coroutines.launch

class MyBalanceViewModel(
    private val coreRepository: CoreRepository
) : ViewModel() {

    var state by mutableStateOf(MyBalanceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                balance = coreRepository.getBalance()
            )
        }
    }

    fun onAction(action: MyBalanceAction) {
        when (action) {
            is MyBalanceAction.OnBalanceChanged -> {
                state = state.copy(
                    balance = action.newBalance
                )
            }

            MyBalanceAction.OnSaveBalance -> {
                viewModelScope.launch {
                    coreRepository.updateBalance(state.balance)

                }
            }
        }
    }
}