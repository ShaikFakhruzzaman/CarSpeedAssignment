package com.example.carspeedassignment.data.notification

import com.android.identity.util.UUID
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.Renter
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class FirebaseNotificationService @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationService {

    override fun notifyCompany(car: Car, currentSpeed: Double, speedLimit: Int) {
        val notificationData = mapOf(
            "carId" to car.licensePlate,
            "currentSpeed" to currentSpeed.toString(),
            "speedLimit" to speedLimit.toString()
        )

        sendFirebaseMessage(notificationData)
    }

    override fun warnUser(renter: Renter, currentSpeed: Double, speedLimit: Int) {
        val warningMessage =
            "Your car is speeding! Current speed: $currentSpeed km/h, Speed limit: $speedLimit km/h"
        sendFirebaseMessage(mapOf("warning" to warningMessage))
    }

    private fun sendFirebaseMessage(notificationData: Map<String, String>) {
        firebaseMessaging.send(
            RemoteMessage.Builder("server-key")
                .setMessageId(UUID.randomUUID().toString())
                .setData(notificationData)
                .build()
        )
    }
}