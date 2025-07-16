package com.example.envtrackerapp

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
