package com.example.spendingtrackerapp.inser_spending.presentation

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import com.example.spendingtrackerapp.core.domain.repository.ImageRepository
import com.example.spendingtrackerapp.inser_spending.domain.SearchImages
import com.example.spendingtrackerapp.inser_spending.domain.UpsertSpendingUseCase
import com.example.spendingtrackerapp.inser_spending.presentation.util.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class InsertSpendingViewModel(
    private val spendingUseCase: UpsertSpendingUseCase,
    private val searchImages: SearchImages
) : ViewModel() {

    var state by mutableStateOf(InsertSpendingState())
        private set

    private val _eventSpending = Channel<SpendingEvent>()
    val eventSpending = _eventSpending.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: InsertAction) {
        when (action) {
            is InsertAction.OnUpdateKilograms -> {
                state = state.copy(
                    kilograms = action.newKilograms
                )
            }

            is InsertAction.OnUpdateName -> {
                state = state.copy(
                    name = action.newName
                )
            }

            is InsertAction.OnUpdatePrice -> {
                state = state.copy(
                    price = action.newPrice
                )
            }

            is InsertAction.OnUpdateQuantity -> {
                state = state.copy(
                    quantity = action.newQuantity
                )

            }

            is InsertAction.OnUpdateSpending -> {
                updateSpending(action.id)
            }

            is InsertAction.OnSaveSpending -> {
                viewModelScope.launch {

                    if (onSave()) {
                        _eventSpending.send(SpendingEvent.SaveSuccess)
                    } else {
                        _eventSpending.send(SpendingEvent.SaveFailed)
                    }
                }

            }

            is InsertAction.PickImage -> {
                state = state.copy(
                    imageUrl = action.imageUrl
                )
            }

            InsertAction.UpdateImageDialogVisibility -> {
                state = state.copy(
                    isImageDialogShowing = !state.isImageDialogShowing
                )
            }

            is InsertAction.UpdateSearchImageQuery -> {
                state = state.copy(
                    imageUrl = action.newQuery
                )
                searchImage(action.newQuery)
            }
        }
    }

    private var searchJob: Job? = null
    private fun searchImage(query: String) {
        searchJob = viewModelScope.launch {
            delay(500)
            searchImages(query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            Log.d(TAG, "Error : ${result.data}")
                            state = state.copy(imageList = emptyList())
                        }

                        is Resource.Loading -> {
                            Log.d(TAG, "is Loading : ${result.data}")

                            state = state.copy(isImageLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(imageList = result.data?.images ?: emptyList())
                        }
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateSpending(id: Int) {
        viewModelScope.launch {

            val updateSpending = SpendingUi(
                spendingId = state.id ?: id,
                name = state.name,
                price = state.price,
                kilograms = state.kilograms,
                quantity = state.quantity,
                dateTime = ZonedDateTime.now(),
                imageUrl = state.imageUrl,
            )

            viewModelScope.launch {
                if (updateSpending.spendingId != null) {
                    val result = spendingUseCase(updateSpending)
                    if (result) {
                        _eventSpending.send(SpendingEvent.SaveSuccess)
                    } else {
                        _eventSpending.send(SpendingEvent.SaveFailed)
                    }
                }
            }


        }
    }

    fun loadSpendingDetails(id: Int) {
        viewModelScope.launch {
            try {
                val spendingDetails = spendingUseCase.getSpending(id = id)
                state = state.copy(
                    id = spendingDetails.spendingId,
                    name = spendingDetails.name,
                    kilograms = spendingDetails.kilograms,
                    quantity = spendingDetails.quantity,
                    price = spendingDetails.price
                )

            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Log.d(TAG, "loadSpendingDetails: ${e.printStackTrace()}")
            }
        }
    }

    fun resetField() {
        state = InsertSpendingState()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun onSave(): Boolean {
        val spendingUi = SpendingUi(
            spendingId = if (state.id != null && state.id != 0) state.id else null,
            name = state.name,
            price = state.price,
            kilograms = state.kilograms,
            quantity = state.quantity,
            dateTime = ZonedDateTime.now(),
            imageUrl = state.imageUrl
        )
        return spendingUseCase(spendingUi)
    }
}