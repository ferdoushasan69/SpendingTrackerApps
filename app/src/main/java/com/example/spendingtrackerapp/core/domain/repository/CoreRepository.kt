package com.example.spendingtrackerapp.core.domain.repository

interface CoreRepository {

    suspend fun updateBalance(balance : Double)

    suspend fun getBalance() : Double
}