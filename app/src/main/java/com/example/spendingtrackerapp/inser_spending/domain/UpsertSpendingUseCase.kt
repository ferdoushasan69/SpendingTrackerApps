package com.example.spendingtrackerapp.inser_spending.domain

import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import com.example.spendingtrackerapp.core.domain.repository.SpendingRepository

class UpsertSpendingUseCase(
    private val spendingRepository: SpendingRepository
) {
    suspend operator fun invoke(spendingUi: SpendingUi):Boolean{
        if (spendingUi.name.isBlank() || spendingUi.price < 0 || spendingUi.kilograms < 0 || spendingUi.quantity < 0){
            return false
        }
        spendingRepository.upsertSpending(spendingUi)
        return true
    }

    suspend fun getSpending(id : Int) : SpendingUi{
        return spendingRepository.getSpending(id)
    }
}