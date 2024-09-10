package com.example.carspeedassignment.domain.usecases

import com.example.carspeedassignment.domain.repositories.SpeedLimitRepository

abstract class GetSpeedUseCase(
private val repository: SpeedLimitRepository
){
    suspend fun getCurrentSpeed(): Double {
        return repository.getCurrentSpeed()
    }

}