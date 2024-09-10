package com.example.carspeedassignment.domain.repositories

import com.example.carspeedassignment.domain.entities.Car


interface SpeedLimitRepository {
    suspend fun getCurrentSpeed(): Double
    suspend fun getSpeedLimit(car: Car): Int?
}