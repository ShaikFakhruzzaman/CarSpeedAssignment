package com.example.carspeedassignment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.entities.SpeedLimit
import com.example.carspeedassignment.domain.usecases.CheckSpeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val checkSpeedUseCase: CheckSpeedUseCase
):ViewModel() {

    private val _carSpeed = MutableLiveData<Double>()  // optional
    val carSpeed: LiveData<Double> = _carSpeed

    fun updateSpeed(car: Car, speedLimit: SpeedLimit) {
        _carSpeed.value = car.currentSpeed
        viewModelScope.launch {
            checkSpeedUseCase.execute(car, speedLimit)
        }
    }
}