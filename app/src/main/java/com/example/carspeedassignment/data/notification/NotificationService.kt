package com.example.carspeedassignment.data.notification

import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.Renter

interface NotificationService {
    fun notifyCompany(car: Car, currentSpeed: Double, speedLimit: Int)
    fun warnUser(renter: Renter, currentSpeed: Double, speedLimit: Int)
}