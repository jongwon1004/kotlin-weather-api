package com.example.kotlin_retfit_sample_weather

import com.example.kotlin_retfit_sample_weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    fun fetchWeather(

        // @Query アノテーションを使用して、リクエストのクエリパラメータを定義
        // "q"は都市の名前を表すクエリパラメータ(例:Tokyo)
        @Query("q") city: String,

        // "lang"はレスポンスの言語を指定するクエリパラメータ(例:ja)
        @Query("lang") lang: String,

        // "appid"は API キーを指定するクエリパラメータ
        // API キーは OpenWeatherMap から取得したものを使用
        @Query("appid") apiKey: String
    ): Call<Weather>
}
