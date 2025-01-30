package com.example.spendingtrackerapp.core.data.repository_impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.spendingtrackerapp.core.data.local.db.SpendingDao
import com.example.spendingtrackerapp.core.data.mapper.toEditSpendingEntity
import com.example.spendingtrackerapp.core.data.mapper.toNewSpendingEntity
import com.example.spendingtrackerapp.core.data.mapper.toSpendingUi
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import com.example.spendingtrackerapp.core.domain.repository.SpendingRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class SpendingRepositoryImpl(
    private val dao: SpendingDao
) : SpendingRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllSpending(): List<SpendingUi> {
        return dao.getAllSpending().map { it.toSpendingUi() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSpendingByDate(zonedDateTime: ZonedDateTime): List<SpendingUi> {
        return dao.getAllSpending().map { it.toSpendingUi() }
            .filter { spendingUi ->
                spendingUi.dateTime.dayOfMonth == zonedDateTime.dayOfMonth
                        && spendingUi.dateTime.month == zonedDateTime.month
                        && spendingUi.dateTime.year == zonedDateTime.year
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDate = mutableSetOf<LocalDate>()
        return dao.getAllDates().map { Instant.parse(it).atZone(ZoneId.of("UTC")) }
            .filter {
                uniqueDate.add(it.toLocalDate())
            }
    }


    override suspend fun deleteSpending(id: Int) {
        dao.deleteSpending(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSpending(id: Int): SpendingUi {
        return dao.getSpending(id = id).toSpendingUi()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertSpending(spendingUi: SpendingUi) {
        if (spendingUi.spendingId != null) {
            dao.upsertSpending(spendingUi.toEditSpendingEntity())
        } else {
            dao.upsertSpending(spendingUi.toNewSpendingEntity())
        }
    }

    override suspend fun getBalance(): Double {
        return dao.getBalance() ?: 0.0
    }
}