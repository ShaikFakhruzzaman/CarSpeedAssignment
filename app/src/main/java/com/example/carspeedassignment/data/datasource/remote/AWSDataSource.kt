package com.example.carspeedassignment.data.datasource.remote

import com.example.carspeedassignment.domain.entities.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AWSDataSource @Inject constructor(

) {
    suspend fun reportSpeed(car: Car) {
        withContext(Dispatchers.IO) {
            val topic = "car_speed/${car.id}"
            publish(topic, car.currentSpeed.toString())
        }
    }

    fun publish(topic: String, speed:String){
        // publish to aws
    }
}