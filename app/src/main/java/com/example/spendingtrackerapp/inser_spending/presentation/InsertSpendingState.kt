package com.example.spendingtrackerapp.inser_spending.presentation

data class InsertSpendingState(
    val id : Int?=null,
    val name : String="",
    val price : Double = 0.0,
    val kilograms : Double = 0.0,
    val quantity : Double = 0.0,
    val imageUrl : String="",
    val isImageDialogShowing : Boolean = false,
    val imageList : List<String> = emptyList(),
    val isImageLoading : Boolean = false,
    val searchImageQuery : String = ""
)