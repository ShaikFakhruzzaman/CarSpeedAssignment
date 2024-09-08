package com.example.carspeedassignment.presentation

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.carspeedassignment.R
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.SpeedLimit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CarSpeedService : LifecycleService() {

    @Inject
    lateinit var carViewModel: CarViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var carId: String? = null
    private var speedLimit: SpeedLimit? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startForegroundService()
    }


    private fun startForegroundService() {
        val notification = createNotification("Monitoring car speed...")
        startForeground(NOTIFICATION_ID, notification)
        monitorCarSpeed()
    }


    private fun createNotification(content: String): Notification {
        val notificationChannelId = "CarSpeedChannel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Car Speed Monitoring",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Car Rental App")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    @SuppressLint("MissingPermission")
    private fun monitorCarSpeed() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location = locationResult.lastLocation
                    location?.let {
                        val speed = location.speed * 3.6 // Convert from m/s to km/h
                        carId?.let { id ->
                            speedLimit?.let { limit ->
                                carViewModel.updateSpeed(Car(id, speed), limit)
                            }
                        }
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        carId = intent?.getStringExtra("CAR_ID")
        val speedLimitValue = intent?.getDoubleExtra("SPEED_LIMIT", 100.0)

        if (carId != null && speedLimitValue != null) {
            speedLimit = SpeedLimit(customerId = carId!!, maxSpeed = speedLimitValue)
        }

        return START_STICKY
    }

    companion object {
        const val NOTIFICATION_ID = 1
    }
}