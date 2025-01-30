package com.example.spendingtrackerapp.core.domain.repository

import com.example.spendingtrackerapp.core.domain.model_ui.Images
import com.example.spendingtrackerapp.inser_spending.presentation.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun searchImage(query : String) : Flow<Resource<Images?>>
}