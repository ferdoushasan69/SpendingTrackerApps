package com.example.spendingtrackerapp.core.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SpendingDao {

    @Upsert
    suspend fun upsertSpending(spendingEntity: SpendingEntity)

    @Query("SELECT * FROM spendingentity")
    suspend fun getAllSpending() : List<SpendingEntity>

    @Query("SELECT * FROM spendingentity WHERE spendingId=:id ")
    suspend fun getSpending(id : Int) : SpendingEntity

    @Query("SELECT SUM(price) FROM spendingentity")
    suspend fun getBalance():Double?

    @Query("DELETe FROM spendingentity WHERE spendingId=:id")
    suspend fun deleteSpending(id: Int)

    @Query("SELECT dateTimeUtc FROM spendingentity")
    suspend fun getAllDates() : List<String>
}