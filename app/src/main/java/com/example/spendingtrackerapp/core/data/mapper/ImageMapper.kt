package com.example.spendingtrackerapp.core.data.mapper

import com.example.spendingtrackerapp.core.data.remote.dto.ImageListDto
import com.example.spendingtrackerapp.core.domain.model_ui.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { it.previewURL ?: "" } ?: emptyList()
    )
}