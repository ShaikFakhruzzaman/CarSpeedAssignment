package com.example.carspeedassignment.domain.usecases

import com.example.carspeedassignment.data.repositories.SpeedRepositoryImpl
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.repositories.SpeedLimitRepository
import javax.inject.Inject

class SpeedLimitUseCase @Inject constructor(
    private val repository: SpeedLimitRepository
) : GetSpeedUseCase(repository){

    fun setSpeedLimit(car: Car, maxSpeed: Int) {
        (repository as? SpeedRepositoryImpl)?.setSpeedLimit(car, maxSpeed)
    }
}