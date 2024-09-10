package com.example.carspeedassignment.data.notification

import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.Renter

class AWSNotificationService:NotificationService {

    override fun notifyCompany(car: Car, currentSpeed: Double, speedLimit: Int) {
        TODO("Not yet implemented")
    }

    override fun warnUser(renter: Renter, currentSpeed: Double, speedLimit: Int) {
        TODO("Not yet implemented")
    }
}