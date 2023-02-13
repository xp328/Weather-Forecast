package com.example.text.network

//根据api结构第二级的模型类--
class PersonWeather {
    val code : Int
    val msg : String
    val data : UserWeather

    constructor(code: Int, msg: String, data: UserWeather) {
        this.code = code
        this.msg = msg
        this.data = data
    }
}

class UserWeather {
    val nowTemperature : Int
    val nowWeatherType : String
    val sevenDaysWeather: Map<String,UserSevenDaysWeatherInt>

    constructor(nowTemperature: Int, nowWeatherType: String, sevenDaysWeather: Map<String,UserSevenDaysWeatherInt>) {
        this.nowTemperature = nowTemperature
        this.nowWeatherType = nowWeatherType
        this.sevenDaysWeather = sevenDaysWeather
    }

    override fun toString(): String {
        return "UserWeather(nowTemperature=$nowTemperature, nowWeatherType='$nowWeatherType', sevenDaysWeather=$sevenDaysWeather)"
    }
}


class UserSevenDaysWeatherInt {
    val weatherType : String
    val temperatureRange: UserTemperatureRange

    constructor(weatherType: String, temperatureRange: UserTemperatureRange) {
        this.weatherType = weatherType
        this.temperatureRange = temperatureRange
    }
}

class UserTemperatureRange {
    val low : Int
    val high : Int

    constructor(low: Int, high: Int) {
        this.low = low
        this.high = high
    }
}
