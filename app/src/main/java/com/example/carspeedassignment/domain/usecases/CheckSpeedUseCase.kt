package com.example.carspeedassignment.domain.usecases

import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.SpeedLimit
import com.example.carspeedassignment.domain.repositories.SpeedRepository
import javax.inject.Inject

class CheckSpeedUseCase @Inject constructor(
    private val speedRepository: SpeedRepository
){
    suspend fun execute(car: Car, speedLimit: SpeedLimit){
        if(car.currentSpeed > speedLimit.maxSpeed){
            speedRepository.reportSpeed(car)
        }
    }
}