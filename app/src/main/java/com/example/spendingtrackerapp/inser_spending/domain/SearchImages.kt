package com.example.spendingtrackerapp.inser_spending.domain

import android.content.ContentValues.TAG
import android.util.Log
import com.example.spendingtrackerapp.core.domain.model_ui.Images
import com.example.spendingtrackerapp.core.domain.repository.ImageRepository
import com.example.spendingtrackerapp.inser_spending.presentation.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchImages(
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(query: String): Flow<Resource<Images?>> {
        if (query.isEmpty()) {

            return flow {
                emit(Resource.Error())
                Log.d(TAG, "invoke: $query")
            }
        }
        return imageRepository.searchImage(query)
    }
}