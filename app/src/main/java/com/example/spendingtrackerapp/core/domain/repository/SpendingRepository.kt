package com.example.spendingtrackerapp.core.domain.repository

import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import java.time.ZonedDateTime

interface SpendingRepository {

    suspend fun getAllSpending():List<SpendingUi>

    suspend fun getSpendingByDate(zonedDateTime: ZonedDateTime) : List<SpendingUi>

    suspend fun getAllDates():List<ZonedDateTime>

    suspend fun deleteSpending(id:Int)

    suspend fun getSpending(id: Int) : SpendingUi

    suspend fun upsertSpending(spendingUi: SpendingUi)

    suspend fun getBalance() : Double
}