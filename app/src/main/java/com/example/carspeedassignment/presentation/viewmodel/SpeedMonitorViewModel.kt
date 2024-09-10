package com.example.carspeedassignment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.domain.usecases.SpeedLimitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeedMonitorViewModel @Inject constructor(
    private val speedLimitUseCase: SpeedLimitUseCase
) : ViewModel() {

    private val _currSpeedFlow = MutableStateFlow(0.0)
    val currSpeedFlow: StateFlow<Double> get() = _currSpeedFlow

    private val _currentSpeed = MutableLiveData<Double>()
    val currentSpeed: LiveData<Double> get() = _currentSpeed

    fun setSpeedLimit(car: Car, maxSpeed: Int) {
        speedLimitUseCase.setSpeedLimit(car, maxSpeed)
    }

    fun getCurrentSpeed() {
        viewModelScope.launch {
            _currSpeedFlow.value = speedLimitUseCase.getCurrentSpeed()
        }
    }
}