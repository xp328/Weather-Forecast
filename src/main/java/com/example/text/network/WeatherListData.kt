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

//7日温度数据初始数据
var sevenDaysWeatherItem: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf(
    "0" to UserSevenDaysWeatherInt("台风",UserTemperatureRange(4,20)),
    "1" to UserSevenDaysWeatherInt("雷电",UserTemperatureRange(-1,27)),
    "2" to UserSevenDaysWeatherInt("小雪",UserTemperatureRange(24,34)),
    "3" to UserSevenDaysWeatherInt("大风",UserTemperatureRange(16,33)),
    "4" to UserSevenDaysWeatherInt("小雪",UserTemperatureRange(19,24)),
    "5" to UserSevenDaysWeatherInt("雾霾",UserTemperatureRange(19,28)),
    "6" to UserSevenDaysWeatherInt("雾霾",UserTemperatureRange(7,33)),
)

var sevenDaysWeatherItemTwo: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf(
    "0" to UserSevenDaysWeatherInt("中雪",UserTemperatureRange(12,22)),
    "1" to UserSevenDaysWeatherInt("中雪",UserTemperatureRange(0,31)),
    "2" to UserSevenDaysWeatherInt("小雪",UserTemperatureRange(19,31)),
    "3" to UserSevenDaysWeatherInt("晴",UserTemperatureRange(-1,9)),
    "4" to UserSevenDaysWeatherInt("大风",UserTemperatureRange(10,15)),
    "5" to UserSevenDaysWeatherInt("沙尘暴",UserTemperatureRange(7,35)),
    "6" to UserSevenDaysWeatherInt("中雨",UserTemperatureRange(12,26)),
)
var sevenDaysWeatherItemThree: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf(
    "0" to UserSevenDaysWeatherInt("大雪",UserTemperatureRange(21,34)),
    "1" to UserSevenDaysWeatherInt("大雨",UserTemperatureRange(12,15)),
    "2" to UserSevenDaysWeatherInt("雨夹雪",UserTemperatureRange(24,34)),
    "3" to UserSevenDaysWeatherInt("中雨",UserTemperatureRange(18,32)),
    "4" to UserSevenDaysWeatherInt("阴",UserTemperatureRange(7,8)),
    "5" to UserSevenDaysWeatherInt("中雨",UserTemperatureRange(3,20)),
    "6" to UserSevenDaysWeatherInt("中雨",UserTemperatureRange(1,29)),
)



//7日温度数据集合 通过map里到key值那对于到数据
var sevenDaysWeather : MutableMap<String,MutableMap<String,UserSevenDaysWeatherInt>> = mutableMapOf(
    "广州" to sevenDaysWeatherItem,
    "南宁" to sevenDaysWeatherItemTwo,
    "北京" to sevenDaysWeatherItemThree,
    "上海" to sevenDaysWeatherItem,
)

//图片集合
val  weatherImage : Map<String,Int> = mapOf(
    "冰雹" to R.drawable.ic_hail,
    "大风" to R.drawable.ic_gale,
    "大雪" to R.drawable.ic_snow,
    "雷电" to R.drawable.ic_thunder,
    "多云" to R.drawable.ic_cloudy,
    "大雨" to R.drawable.ic_rain,
    "雷阵雨" to R.drawable.ic_thunder_storm,
    "晴" to R.drawable.ic_sunny,
    "沙尘暴" to R.drawable.ic_sandstorm,
    "雾" to R.drawable.ic_fog,
    "台风" to R.drawable.ic_pyphoon,
    "雾霾" to R.drawable.ic_haze,
    "小雪" to R.drawable.ic_light_snow,
    "小雨" to R.drawable.ic_light_rain,
    "阴" to R.drawable.ic_overcast,
    "雨夹雪" to R.drawable.ic_sleet,
    "中雪" to R.drawable.ic_moderate_snow,
    "中雨" to R.drawable.ic_moderate_rain,
)

//更多页面的初始数据集合
var wListData :MutableList<WeatherListData>  = mutableListOf(
    WeatherListData("广州","25","多云",R.drawable.ic_cloudy, sevenDaysWeather["广州"]!!),
    WeatherListData("南宁","18","阴",R.drawable.ic_overcast, sevenDaysWeather["南宁"]!!),
    WeatherListData("北京","28","晴",R.drawable.ic_sunny, sevenDaysWeather["北京"]!!),
    WeatherListData("上海","22","多云",R.drawable.ic_cloudy, sevenDaysWeatherItem),
)


//省名集合
lateinit var provinceNameList: List<String>
//城市名集合
lateinit var citiesNameList: ArrayList<String>

var tempArray: ArrayList<ArrayList<String>> = ArrayList()//
//var provinceName:String = "北京"
