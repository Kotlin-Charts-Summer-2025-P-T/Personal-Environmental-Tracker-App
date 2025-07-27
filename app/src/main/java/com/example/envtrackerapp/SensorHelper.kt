package com.example.envtrackerapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorHelper(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val tempSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    private val humiditySensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
    private val lightSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    var tempValue: Float = 0f
    var humidityValue: Float = 0f
    var lightValue: Float = 0f

    fun startListening() {
        tempSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        humiditySensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: android.hardware.SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> tempValue = event.values[0]
            Sensor.TYPE_RELATIVE_HUMIDITY -> humidityValue = event.values[0]
            Sensor.TYPE_LIGHT -> lightValue = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
