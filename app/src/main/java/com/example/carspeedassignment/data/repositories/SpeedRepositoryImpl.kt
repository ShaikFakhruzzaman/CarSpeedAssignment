package com.example.carspeedassignment.data.repositories

import com.example.carspeedassignment.data.datasource.remote.AWSDataSource
import com.example.carspeedassignment.data.datasource.remote.FirebaseDataSource
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.repositories.SpeedRepository
import javax.inject.Inject

class SpeedRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val awsDataSource: AWSDataSource
) : SpeedRepository {

    override suspend fun reportSpeed(car: Car) {
        firebaseDataSource.reportSpeed(car)   // either firebase or aws
//        awsDataSource.reportSpeed(carId, speed)
    }
}
