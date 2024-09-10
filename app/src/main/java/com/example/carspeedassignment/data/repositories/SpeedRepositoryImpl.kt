package com.example.carspeedassignment.data.repositories

import com.example.carspeedassignment.data.provider.GpsSpeedProvider
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.repositories.SpeedLimitRepository
import javax.inject.Inject

class SpeedRepositoryImpl @Inject constructor(
    private val gpsSpeedProvider: GpsSpeedProvider
) : SpeedLimitRepository {

    private val carSpeedLimits = mutableMapOf<String, Int>() // to Store speed limit

    override suspend fun getCurrentSpeed(): Double {
        return gpsSpeedProvider.getCurrentSpeed()
    }

    override suspend fun getSpeedLimit(car: Car): Int? {
        return carSpeedLimits[car.licensePlate]
    }

    fun setSpeedLimit(car: Car, speedLimit: Int) {
        carSpeedLimits[car.licensePlate] = speedLimit
    }
}