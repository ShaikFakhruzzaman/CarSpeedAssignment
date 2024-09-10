package com.example.carspeedassignment.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.carspeedassignment.domain.entities.Car
import com.example.carspeedassignment.presentation.theme.CarSpeedAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val speedMonitorViewModel: SpeedMonitorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startForeGService()

        val car = Car("xyz123", "So-and-So")
        speedMonitorViewModel.setSpeedLimit(car, 100)
        speedMonitorViewModel.apply {
            //LiveData to update Ui
            currentSpeed.observe(this@MainActivity) { speed ->
                Log.d("zama", "Current Speed: $speed")
            }

            //Flow to update Ui
            CoroutineScope(Dispatchers.IO).launch {
                currSpeedFlow.collect {
                    // updates currentSpeed to UI
                }
            }
        }

        setContent {
            CarSpeedAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startForeGService() {
        val intent = Intent(this, CarSpeedMonitorService::class.java)
        startService(intent)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarSpeedAssignmentTheme {
        Greeting("Android")
    }
}