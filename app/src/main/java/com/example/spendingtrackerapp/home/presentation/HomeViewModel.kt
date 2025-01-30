package com.example.spendingtrackerapp.home.presentation

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository
import com.example.spendingtrackerapp.core.domain.repository.SpendingRepository
import com.example.spendingtrackerapp.utils.randomColor
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HomeViewModel(
    private val coreRepository: CoreRepository,
    private val spendingRepository: SpendingRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadingHomeViewBalance -> {
                loadSpendingListAndBalance()
            }

            is HomeAction.OnDateChanged -> {
                val newDate = state.dateList[action.newDate]
                viewModelScope.launch {
                    state = state.copy(
                        pickDate = newDate,
                        spending = getSpendingListByDate(newDate)
                    )
                }

            }

            is HomeAction.OnDeleteSpending -> {
                viewModelScope.launch {
                    spendingRepository.deleteSpending(action.spendingId)
                    state = state.copy(
                        spending = getSpendingListByDate(state.pickDate),
                        dateList = spendingRepository.getAllDates(),
                        balance = coreRepository.getBalance() - spendingRepository.getBalance()
                    )
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSpendingListAndBalance() {
        Log.d(TAG, "loadSpendingListAndBalance: loaded")
        viewModelScope.launch {
            val allDate = spendingRepository.getAllDates()
            state = state.copy(
                spending = getSpendingListByDate(allDate.lastOrNull() ?: ZonedDateTime.now()),
                balance = coreRepository.getBalance() - spendingRepository.getBalance(),
                pickDate = allDate.lastOrNull() ?: ZonedDateTime.now(),
                dateList = allDate

            )
        }
    }

    private suspend fun getSpendingListByDate(zonedDateTime: ZonedDateTime): List<SpendingUi> {
        return spendingRepository.getSpendingByDate(zonedDateTime).reversed()
            .map { it.copy(color = randomColor()) }
    }

}

