package com.example.text.network

import com.example.text.R

//更多页面的模型类
class WeatherListData {
    val text : String
    val temperature : String
    val weather : String
    val weatherImage : Int
    private val sevenDaysWeather: Map<String,UserSevenDaysWeatherInt>

    constructor(text: String, temperature: String, weather: String, weatherImage: Int, sevenDaysWeather: Map<String,UserSevenDaysWeatherInt>) {
        this.text = text
        this.temperature = temperature
        this.weather = weather
        this.weatherImage = weatherImage
        this.sevenDaysWeather = sevenDaysWeather
    }

    override fun toString(): String {
        return "WeatherListData(text='$text', temperature='$temperature', weather='$weather', weatherImage=$weatherImage)"
    }


}

