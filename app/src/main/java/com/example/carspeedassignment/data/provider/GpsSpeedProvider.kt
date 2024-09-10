package com.example.carspeedassignment.data.provider

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GpsSpeedProvider @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentSpeed(): Double {
        val location = fusedLocationProviderClient.lastLocation.await()
        return location?.speed?.times(3.6) ?: 0.0
    }
}