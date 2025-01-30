package com.example.spendingtrackerapp.inser_spending.di

import com.example.spendingtrackerapp.core.data.repository_impl.ImageRepositoryImpl
import com.example.spendingtrackerapp.core.domain.repository.ImageRepository
import com.example.spendingtrackerapp.inser_spending.domain.SearchImages
import com.example.spendingtrackerapp.inser_spending.domain.UpsertSpendingUseCase
import com.example.spendingtrackerapp.inser_spending.presentation.InsertSpendingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val insertSpending = module {
    factory {
        UpsertSpendingUseCase(get())
    }
    factory {
        SearchImages(get())
    }
    viewModel { InsertSpendingViewModel(spendingUseCase = get(),get()) }
}