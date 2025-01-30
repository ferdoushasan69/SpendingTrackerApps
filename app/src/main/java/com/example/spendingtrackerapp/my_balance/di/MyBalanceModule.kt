package com.example.spendingtrackerapp.my_balance.di

import com.example.spendingtrackerapp.my_balance.presentation.MyBalanceViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myBalanceModule = module {
    viewModel { MyBalanceViewModel(get()) }
}