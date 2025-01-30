package com.example.spendingtrackerapp.core.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpendingEntity(
    @PrimaryKey(autoGenerate = true)
    val spendingId  : Int?=null,
    val name : String,
    val price : Double,
    val kilograms: Double,
    val quantity : Double,
    val dateTimeUtc  : String,
    val imageUrl : String

)
