package com.example.carspeedassignment.domain.repositories

import com.example.carspeedassignment.domain.entities.Car


interface SpeedRepository {
    suspend fun reportSpeed(car: Car)
}