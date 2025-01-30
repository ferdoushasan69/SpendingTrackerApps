package com.example.spendingtrackerapp.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SpendingEntity::class], version = 2)
abstract class SpendingDatabase : RoomDatabase() {
    abstract val dao : SpendingDao
}