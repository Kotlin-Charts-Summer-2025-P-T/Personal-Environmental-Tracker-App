package com.example.envtrackerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.Legend
import android.os.Handler
import com.example.envtrackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var sensorHelper: SensorHelper
    private lateinit var binding: ActivityMainBinding
    private lateinit var chart: LineChart
    private val handler = Handler()
    private val updateInterval = 2000L // update every 2 seconds
    private var index = 0f

    private val tempEntries = ArrayList<Entry>()
    private val humidityEntries = ArrayList<Entry>()
    private val lightEntries = ArrayList<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chart = binding.lineChart
        sensorHelper = SensorHelper(this)
        sensorHelper.startListening()

        setupChart()
        startPlotting()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.stopListening()
        handler.removeCallbacksAndMessages(null)
    }

    private fun setupChart() {
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)

        chart.legend.apply {
            form = Legend.LegendForm.LINE
            isEnabled = true
        }
    }

    private fun startPlotting() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                index += 1f

                tempEntries.add(Entry(index, sensorHelper.tempValue))
                humidityEntries.add(Entry(index, sensorHelper.humidityValue))
                lightEntries.add(Entry(index, sensorHelper.lightValue))

                val tempDataSet = LineDataSet(tempEntries, "Temp (°C)").apply {
                    color = android.graphics.Color.RED
                    setCircleColor(android.graphics.Color.RED)
                    lineWidth = 2f
                    setDrawCircles(false)
                }

                val humidityDataSet = LineDataSet(humidityEntries, "Humidity (%)").apply {
                    color = android.graphics.Color.BLUE
                    setCircleColor(android.graphics.Color.BLUE)
                    lineWidth = 2f
                    setDrawCircles(false)
                }

                val lightDataSet = LineDataSet(lightEntries, "Light (lux)").apply {
                    color = android.graphics.Color.GREEN
                    setCircleColor(android.graphics.Color.GREEN)
                    lineWidth = 2f
                    setDrawCircles(false)
                }

                val data = LineData(tempDataSet, humidityDataSet, lightDataSet)
                chart.data = data
                chart.invalidate()

                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)
    }
}
