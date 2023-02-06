package com.example.text.network

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForecastWeather {
    val time : String
    val image : Int
    val weather : String
    val low : Int
    val high : Int

    constructor(time: String, image: Int, weather: String, low: Int, high: Int) {
        this.time = time
        this.image = image
        this.weather = weather
        this.low = low
        this.high = high
    }
}

/*//时间
val current = LocalDateTime.now()
val formatter = DateTimeFormatter.ofPattern("MM月dd日")
val formatted = current.format(formatter)*/


var FListDataItem : MutableMap<String,ForecastWeather> = mutableMapOf(
    "0" to ForecastWeather("11月23日", weatherImage[sevenDaysWeatherItem["0"]?.weatherType] as Int, sevenDaysWeatherItem["0"]!!.weatherType,sevenDaysWeatherItem["0"]!!.temperatureRange.low,sevenDaysWeatherItem["0"]!!.temperatureRange.high),
    "1" to ForecastWeather("11月24日", weatherImage[sevenDaysWeatherItem["1"]?.weatherType] as Int, sevenDaysWeatherItem["1"]!!.weatherType,sevenDaysWeatherItem["1"]!!.temperatureRange.low,sevenDaysWeatherItem["1"]!!.temperatureRange.high),
    "2" to ForecastWeather("11月25日", weatherImage[sevenDaysWeatherItem["2"]?.weatherType] as Int, sevenDaysWeatherItem["2"]!!.weatherType,sevenDaysWeatherItem["2"]!!.temperatureRange.low,sevenDaysWeatherItem["2"]!!.temperatureRange.high),
    "3" to ForecastWeather("11月26日", weatherImage[sevenDaysWeatherItem["3"]?.weatherType] as Int, sevenDaysWeatherItem["3"]!!.weatherType,sevenDaysWeatherItem["3"]!!.temperatureRange.low,sevenDaysWeatherItem["3"]!!.temperatureRange.high),
    "4" to ForecastWeather("11月27日", weatherImage[sevenDaysWeatherItem["4"]?.weatherType] as Int, sevenDaysWeatherItem["4"]!!.weatherType,sevenDaysWeatherItem["4"]!!.temperatureRange.low,sevenDaysWeatherItem["4"]!!.temperatureRange.high),
    "5" to ForecastWeather("11月28日", weatherImage[sevenDaysWeatherItem["5"]?.weatherType] as Int, sevenDaysWeatherItem["5"]!!.weatherType,sevenDaysWeatherItem["5"]!!.temperatureRange.low,sevenDaysWeatherItem["5"]!!.temperatureRange.high),
    "6" to ForecastWeather("11月29日", weatherImage[sevenDaysWeatherItem["6"]?.weatherType] as Int, sevenDaysWeatherItem["6"]!!.weatherType,sevenDaysWeatherItem["6"]!!.temperatureRange.low,sevenDaysWeatherItem["6"]!!.temperatureRange.high),
)

//测试
var FListDataItemTwo : MutableMap<String,ForecastWeather> = mutableMapOf(
    "0" to ForecastWeather("10月23日", weatherImage[sevenDaysWeatherItemTwo["0"]?.weatherType] as Int, sevenDaysWeatherItemTwo["0"]!!.weatherType,sevenDaysWeatherItemTwo["0"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["0"]!!.temperatureRange.high),
    "1" to ForecastWeather("10月24日", weatherImage[sevenDaysWeatherItemTwo["1"]?.weatherType] as Int, sevenDaysWeatherItemTwo["1"]!!.weatherType,sevenDaysWeatherItemTwo["1"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["1"]!!.temperatureRange.high),
    "2" to ForecastWeather("10月25日", weatherImage[sevenDaysWeatherItemTwo["2"]?.weatherType] as Int, sevenDaysWeatherItemTwo["2"]!!.weatherType,sevenDaysWeatherItemTwo["2"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["2"]!!.temperatureRange.high),
    "3" to ForecastWeather("10月26日", weatherImage[sevenDaysWeatherItemTwo["3"]?.weatherType] as Int, sevenDaysWeatherItemTwo["3"]!!.weatherType,sevenDaysWeatherItemTwo["3"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["3"]!!.temperatureRange.high),
    "4" to ForecastWeather("10月27日", weatherImage[sevenDaysWeatherItemTwo["4"]?.weatherType] as Int, sevenDaysWeatherItemTwo["4"]!!.weatherType,sevenDaysWeatherItemTwo["4"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["4"]!!.temperatureRange.high),
    "5" to ForecastWeather("10月28日", weatherImage[sevenDaysWeatherItemTwo["5"]?.weatherType] as Int, sevenDaysWeatherItemTwo["5"]!!.weatherType,sevenDaysWeatherItemTwo["5"]!!.temperatureRange.low,sevenDaysWeatherItemTwo["5"]!!.temperatureRange.high),
    "6" to ForecastWeather("10月29日", weatherImage[sevenDaysWeatherItemTwo["6"]?.weatherType] as Int, sevenDaysWeatherItemTwo["6"]!!.weatherType,sevenDaysWeatherItemTwo["6"]!!.temperatureRange.low,sevenDaysWeatherItem["6"]!!.temperatureRange.high),
)

var FListDataItemThree : MutableMap<String,ForecastWeather> = mutableMapOf(
    "0" to ForecastWeather("9月23日", weatherImage[sevenDaysWeatherItemThree["0"]?.weatherType] as Int, sevenDaysWeatherItemThree["0"]!!.weatherType,sevenDaysWeatherItemThree["0"]!!.temperatureRange.low,sevenDaysWeatherItemThree["0"]!!.temperatureRange.high),
    "1" to ForecastWeather("9月24日", weatherImage[sevenDaysWeatherItemThree["1"]?.weatherType] as Int, sevenDaysWeatherItemThree["1"]!!.weatherType,sevenDaysWeatherItemThree["1"]!!.temperatureRange.low,sevenDaysWeatherItemThree["1"]!!.temperatureRange.high),
    "2" to ForecastWeather("9月25日", weatherImage[sevenDaysWeatherItemThree["2"]?.weatherType] as Int, sevenDaysWeatherItemThree["2"]!!.weatherType,sevenDaysWeatherItemThree["2"]!!.temperatureRange.low,sevenDaysWeatherItemThree["2"]!!.temperatureRange.high),
    "3" to ForecastWeather("9月26日", weatherImage[sevenDaysWeatherItemThree["3"]?.weatherType] as Int, sevenDaysWeatherItemThree["3"]!!.weatherType,sevenDaysWeatherItemThree["3"]!!.temperatureRange.low,sevenDaysWeatherItemThree["3"]!!.temperatureRange.high),
    "4" to ForecastWeather("9月27日", weatherImage[sevenDaysWeatherItemThree["4"]?.weatherType] as Int, sevenDaysWeatherItemThree["4"]!!.weatherType,sevenDaysWeatherItemThree["4"]!!.temperatureRange.low,sevenDaysWeatherItemThree["4"]!!.temperatureRange.high),
    "5" to ForecastWeather("9月28日", weatherImage[sevenDaysWeatherItemThree["5"]?.weatherType] as Int, sevenDaysWeatherItemThree["5"]!!.weatherType,sevenDaysWeatherItemThree["5"]!!.temperatureRange.low,sevenDaysWeatherItemThree["5"]!!.temperatureRange.high),
    "6" to ForecastWeather("9月29日", weatherImage[sevenDaysWeatherItemThree["6"]?.weatherType] as Int, sevenDaysWeatherItemThree["6"]!!.weatherType,sevenDaysWeatherItemThree["6"]!!.temperatureRange.low,sevenDaysWeatherItem["6"]!!.temperatureRange.high),
)

var FListData : MutableList<MutableMap<String,ForecastWeather>> = mutableListOf(
    FListDataItem,
    FListDataItemTwo,
    FListDataItemThree,
    FListDataItem,
)