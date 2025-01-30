package com.example.spendingtrackerapp.inser_spending.presentation

sealed interface InsertAction {
    data class OnUpdateName(val newName : String) : InsertAction
    data class OnUpdatePrice(val newPrice : Double) : InsertAction
    data class OnUpdateKilograms(val newKilograms : Double) : InsertAction
    data class OnUpdateQuantity(val newQuantity : Double) : InsertAction
    data class UpdateSearchImageQuery(val newQuery : String) : InsertAction
    data class PickImage(val imageUrl : String) : InsertAction
    data object UpdateImageDialogVisibility:InsertAction
    data class OnUpdateSpending(val id : Int) : InsertAction

    data class OnSaveSpending(val id : Int?=null) : InsertAction
}