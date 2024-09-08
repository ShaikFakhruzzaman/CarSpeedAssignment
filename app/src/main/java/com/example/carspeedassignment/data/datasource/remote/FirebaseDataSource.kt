package com.example.carspeedassignment.data.datasource.remote

import com.example.carspeedassignment.domain.entities.Car
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val database: FirebaseDatabase
) {
    suspend fun reportSpeed(car: Car) {
        database.reference.child("speed_data")
            .child(car.id)
            .setValue(car.currentSpeed)
            .await()
    }
}