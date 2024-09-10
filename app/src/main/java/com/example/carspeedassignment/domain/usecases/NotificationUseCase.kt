package com.example.carspeedassignment.domain.usecases

import com.example.carspeedassignment.data.notification.NotificationService
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.Renter
import com.example.carspeedassignment.domain.repositories.SpeedLimitRepository
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val repository: SpeedLimitRepository,
    private val notificationService: NotificationService
):GetSpeedUseCase(repository) {

    suspend fun checkSpeed(car: Car) {
        val currentSpeed = getCurrentSpeed()
        val speedLimit = repository.getSpeedLimit(car)

        if (speedLimit != null && currentSpeed > speedLimit) {
            val renter = getCarUser(car)
            notificationService.notifyCompany(car, currentSpeed, speedLimit)
            notificationService.warnUser(renter, currentSpeed, speedLimit)
        }
    }

    private fun getCarUser(car: Car): Renter {
        return Renter("shaik", "shaik@mail.com")
    }
}