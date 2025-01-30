package com.example.spendingtrackerapp.home.di

import com.example.spendingtrackerapp.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get(),get()) }
}