package com.example.envtrackerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class MainActivity : AppCompatActivity() {
    lateinit var sensorHelper: SensorHelper
    lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorHelper = SensorHelper(this)
        sensorHelper.registerSensors()

        lineChart = findViewById(R.id.lineChart)

        plotData()
    }

    fun plotData() {
        val entries = listOf(
            Entry(1f, sensorHelper.tempValue),
            Entry(2f, sensorHelper.humidityValue),
            Entry(3f, sensorHelper.lightValue)
        )
        val dataSet = LineDataSet(entries, "Sensor Readings")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

}