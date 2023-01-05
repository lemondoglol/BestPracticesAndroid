package com.example.bestpracticesapplication

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SensorEventListener {

    private val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var sigSensor: Sensor? = null
    private var acceleratorSensor: Sensor? = null
    private var sigTriggerEventListener: TriggerEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // get a list of sensors
//        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sigSensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
        acceleratorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sigTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
//                Toast.makeText(this@MainActivity, "Event: ${event.values}", Toast.LENGTH_SHORT).show()
            }
        }
        sigSensor?.let {
            sensorManager.requestTriggerSensor(sigTriggerEventListener, it)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor) {
            acceleratorSensor -> {
                val curTime = System.currentTimeMillis()
                val diffTime = curTime - viewModel.prevTime
                if (diffTime > 1000) {
                    val newX = event.values[0]
                    val newY = event.values[1]
                    val newZ = event.values[2]
                    val speed = abs(newX + newY + newZ - viewModel.prevX - viewModel.prevY - viewModel.prevZ) / diffTime * 100000

                    if (speed > 800) {
//                        Toast.makeText(this@MainActivity, "Shacking $speed", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.prevX = newX
                    viewModel.prevY = newY
                    viewModel.prevZ = newZ
                    viewModel.prevTime = curTime
                }
            }
            lightSensor -> {
//                Log.d("Tuna", "Light Sensor: ")
            }
            else -> {}
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        sensorManager.cancelTriggerSensor(sigTriggerEventListener, sigSensor)
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL // samplingPeriodUs
            )
        }
        acceleratorSensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL // samplingPeriodUs
            )
        }
    }
}