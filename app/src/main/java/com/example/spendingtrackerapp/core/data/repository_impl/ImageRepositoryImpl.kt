package com.example.spendingtrackerapp.core.data.repository_impl

import android.content.ContentValues.TAG
import android.util.Log
import com.example.spendingtrackerapp.core.data.mapper.toImages
import com.example.spendingtrackerapp.core.data.remote.ImageApi
import com.example.spendingtrackerapp.core.domain.model_ui.Images
import com.example.spendingtrackerapp.core.domain.repository.ImageRepository
import com.example.spendingtrackerapp.inser_spending.presentation.util.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepositoryImpl(
    private val imageApi: ImageApi
) : ImageRepository{
    override suspend fun searchImage(query: String): Flow<Resource<Images?>> = flow {
        Log.d(TAG, "imageRepositoryImpl : $query")
        try {
            val apiData = imageApi.getImage(query = query)
            val mapData = apiData?.toImages()
            Log.d(TAG, "ImageRepositoryImpl: ${mapData?.images}")
            emit(Resource.Success(mapData))
        }catch (e : Exception){
            e.printStackTrace()
            if (e is CancellationException) throw  e
            Log.d(TAG, "ImageRepositoryImpl: ${e.cause}")
            emit(Resource.Error())

        }
    }
}