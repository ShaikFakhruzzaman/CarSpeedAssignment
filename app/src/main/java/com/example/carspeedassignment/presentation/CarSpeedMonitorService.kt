package com.example.carspeedassignment.presentation

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.usecases.NotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class CarSpeedMonitorService : Service() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    private lateinit var car: Car
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startForegroundNotification()
        car = Car("xyz123", "So-and-So")
        serviceScope.launch {
            while (true) {
                notificationUseCase.checkSpeed(car)
                delay(5000L)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundNotification() {
        val notification = Notification.Builder(this, "SpeedMonitorChannel")
            .setContentTitle("Speed Monitor Running")
            .setContentText("Monitoring car speed")
            .setSmallIcon(android.R.drawable.ic_menu_compass).build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}