package com.example.spendingtrackerapp.core.data.repository_impl

import android.content.SharedPreferences
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository

class CoreRepositoryImpl(
    private val preferences: SharedPreferences
) : CoreRepository {
    override suspend fun updateBalance(balance: Double) {
        preferences.edit().putFloat(KEY_VALUE,balance.toFloat()).apply()
    }

    override suspend fun getBalance(): Double {
       return preferences.getFloat(KEY_VALUE,0f).toDouble()
    }

    companion object{
        const val KEY_VALUE = "KEY_VALUE"
    }
}