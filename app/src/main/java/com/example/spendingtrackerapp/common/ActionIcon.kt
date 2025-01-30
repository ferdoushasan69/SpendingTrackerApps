package com.example.spendingtrackerapp.common

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface ActionIcon {
    data class Icon(val imageVector: ImageVector) : ActionIcon
    data class Text(val text : String) : ActionIcon

}