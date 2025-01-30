package com.example.spendingtrackerapp

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.spendingtrackerapp.core.di.coreModule
import com.example.spendingtrackerapp.home.di.homeModule
import com.example.spendingtrackerapp.inser_spending.di.insertSpending
import com.example.spendingtrackerapp.my_balance.di.myBalanceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseClass : Application() {

    override fun onCreate() {
        super.onCreate()
        val key = BuildConfig.API_KEY
        Log.d(TAG, "onCreate: $key")
        startKoin{
            androidContext(this@BaseClass)
            modules(
                coreModule,
                myBalanceModule,
                myBalanceModule,
                homeModule,
                insertSpending
            )
        }
    }
}