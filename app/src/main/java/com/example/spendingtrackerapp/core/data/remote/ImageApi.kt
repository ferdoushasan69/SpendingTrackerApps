package com.example.spendingtrackerapp.core.data.remote

import com.example.spendingtrackerapp.BuildConfig
import com.example.spendingtrackerapp.core.data.remote.dto.ImageListDto
import com.example.spendingtrackerapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET(Constant.END_POINT)
    suspend fun getImage(
        @Query("key") apiKey : String = BuildConfig.API_KEY,
        @Query("q")query:String,
    ) : ImageListDto?
}