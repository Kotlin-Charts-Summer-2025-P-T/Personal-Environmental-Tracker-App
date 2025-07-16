package com.example.envtrackerapp

object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Auto JSON parsing
            .build()
    }

    val api: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}
