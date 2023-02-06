package com.example.text.fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.text.MainActivity
import com.example.text.R
import com.example.text.adapter.ExpandableListViewAdapter
import com.example.text.databinding.FragmentAddBinding
import com.example.text.databinding.ItemExlistGroupBinding
import com.example.text.databinding.ItemExlistItemBinding
import com.example.text.network.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URI
import java.net.URL
import kotlin.concurrent.thread
import kotlin.properties.Delegates


class AddFragment : Fragment() {
    private var _bd: FragmentAddBinding? = null
    private val bd: FragmentAddBinding get() = _bd!!



//    var tempArray: ArrayList<ArrayList<String>> = ArrayList()//
    var text: String = ""//右上角的文字
    var addFlag = false

    init {
        //循环省名 按顺序获取城市

        Log.d("TempArrayInit=====",tempArray.toString())

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bd = FragmentAddBinding.inflate(inflater, container, false)
        Log.d("TempArrayOCV",tempArray.size.toString())

        ExpandableListView()

        //设置右上角的文字，点击后显示选择的文字
        bd.iAddNextText.setOnClickListener {
            if (text != "") {
                bd.iAddNextText.text = text
            }
        }

        //添加
        bd.iAddButton.setOnClickListener {
            if (text != "") {
                //调用Post方法
                addGetWeather(text)
            }else{
                Toast.makeText(context,"没有选择",Toast.LENGTH_SHORT).show()
            }
        }

        return bd.root
    }

    //设置ExpandableListView数据
    protected fun ExpandableListView() {

        provinceNameList.forEach {
            getCitiesData(it)
        }
        //设置二级列表ExpandableListView对应的Adapter
//        println("tempArray${tempArray}")
        Log.d("TempArray",tempArray.toString())
        var adapter = ExpandableListViewAdapter(provinceNameList, tempArray)
//        Log.d("TempArray",tempArray.toString())
        bd.expandableList.setAdapter(adapter)

        //一级列表对应的点击事件
        bd.expandableList.setOnGroupClickListener { parent, v, groupPosition, id ->
            println("11")

            false
        }

        //二级列表对应的点击事件
        bd.expandableList.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
//            Log.d("TempArray",tempArray.toString())
            bd.iAddNextText.text = tempArray[groupPosition][childPosition]
            text = tempArray[groupPosition][childPosition]
            Log.d("ITTEMP","------------------${tempArray[groupPosition][childPosition]}")
            /*Toast.makeText(
                context,
                "已选择：${tempArray.get(groupPosition).get(childPosition)}",
                Toast.LENGTH_LONG
            ).show()*/
            false
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getCitiesData(provinceNames: String) {
        var conn: HttpURLConnection? = null
        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=$provinceNames"
        var temp = provinceNames

        GlobalScope.async {
            //再循环 确保按顺序
            provinceNameList.forEach {
                conn = URL(citiesUriString).openConnection() as HttpURLConnection
                conn!!.connect()
                conn!!.inputStream.use { input ->
                    //在use里执行的顺序会混乱 通过if判断 但是会出问题，需要时间判断，如果没判断完就点击就会保存 大约5秒，每次都是重复执行
                    //  思路1：将代码移动到上一个页面执行，进到添加页面到时候数据就已经加载完
                    if (it == temp) {
//                        Log.d("ITTEMP","$it----$temp")
                        var data = input.bufferedReader().readText()
                        var jsonDecode = Gson().fromJson(data, Person::class.java)
                        citiesNameList = jsonDecode.data
                        tempArray.add(citiesNameList)
                    }
                }
            }

        }
    }

    //ApiPost
    fun addGetWeather(city: String) {
        var conn: HttpURLConnection? = null
        var uriString = "http://175.178.70.219:8083/api/weather/today"
        /*GlobalScope.async {
            conn = URL(uriString).openConnection() as HttpURLConnection
            conn!!.requestMethod = "POST"//指定POST请求
            conn!!.doOutput = true //设置请求过程可以传递参数给服务器
            conn!!.useCaches = false
            conn!!.doInput = true
            conn!!.addRequestProperty("Content-Type", "application/json")


            val map = mapOf("city" to city)
            val json = Gson().toJson(map)

            conn!!.outputStream.use {
                val byteArray = json.toByteArray()
                it.write(byteArray, 0, byteArray.size)
            }

            val code = conn!!.responseCode
            if (code == HttpURLConnection.HTTP_OK) {
                conn!!.inputStream.use {
                    val readText = BufferedReader(it.bufferedReader()).readText()
                    var jsonDecode = Gson().fromJson(readText, PersonWeather::class.java)
                    Log.d("TAG", city)
                    Log.d("TAG", "${jsonDecode.data.nowTemperature}---${jsonDecode.data.nowWeatherType}\n${jsonDecode.data.sevenDaysWeather}")
                }
            }
//            //post请求参数
//            val param = "city"
//            DataOutputStream(conn!!.outputStream).use {
//                it.writeBytes(param)
//            }
//            conn!!.connect()
//            try {
//                conn!!.inputStream.use {
//                    Log.d("weather", "11")
//                    val data = it.bufferedReader().readText()
//                    var jsonDecode = Gson().fromJson(data, Person::class.java)
//                    Log.d("weather", jsonDecode.toString())
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

        }*/
        thread {
            conn = URL(uriString).openConnection() as HttpURLConnection
            conn!!.requestMethod = "POST"//指定POST请求
            conn!!.doOutput = true //设置请求过程可以传递参数给服务器
            conn!!.useCaches = false //设置不使用缓存
            conn!!.doInput = true //设置读取内容
            conn!!.addRequestProperty("Content-Type", "application/json") //  请求头


            val map = mapOf("city" to city)
            val json = Gson().toJson(map)

            conn!!.outputStream.use {
                val byteArray = json.toByteArray()
                it.write(byteArray, 0, byteArray.size)
            }

            val code = conn!!.responseCode
            if (code == HttpURLConnection.HTTP_OK) {
                conn!!.inputStream.use {
                    val readText = BufferedReader(it.bufferedReader()).readText()
                    var jsonDecode = Gson().fromJson(readText, PersonWeather::class.java)

                    //7日数据添加

//                    Log.d("ApiGET", (sevenDaysWeather.size - 1).toString())
/*                    val tempWeatherType = jsonDecode.data.sevenDaysWeather["0"]?.weatherType
                    val low = jsonDecode.data.sevenDaysWeather["0"]?.temperatureRange?.low
                    val high = jsonDecode.data.sevenDaysWeather["0"]?.temperatureRange?.high*/

//                    //复用
//                    if (tempWeatherType != null && low != null && high != null){
////                        sevenDaysWeatherItem["0"] = UserSevenDaysWeatherInt("台风",UserTemperatureRange(4,20))
//                        sevenDaysWeatherItem["0"] = UserSevenDaysWeatherInt(tempWeatherType,UserTemperatureRange(low,high))
//                    }

/*
                    var addSevenDaysWeatherItem: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf(
                        "0" to UserSevenDaysWeatherInt("台风",UserTemperatureRange(4,20)),
                        "1" to UserSevenDaysWeatherInt("雷电",UserTemperatureRange(-1,27)),
                        "2" to UserSevenDaysWeatherInt("小雪",UserTemperatureRange(24,34)),
                        "3" to UserSevenDaysWeatherInt("大风",UserTemperatureRange(16,33)),
                        "4" to UserSevenDaysWeatherInt("小雪",UserTemperatureRange(19,24)),
                        "5" to UserSevenDaysWeatherInt("雾霾",UserTemperatureRange(19,28)),
                        "6" to UserSevenDaysWeatherInt("雾霾",UserTemperatureRange(7,33)),
                    )
*/


//                    jsonDecode.data.sevenDaysWeather.size

                    //添加7日温度数据
                         var addSevenDaysWeatherItem: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf()
                         for (i in 0 until jsonDecode.data.sevenDaysWeather.size){
                             addSevenDaysWeatherItem["$i"] = UserSevenDaysWeatherInt(
                                 jsonDecode.data.sevenDaysWeather["$i"]!!.weatherType,
                                 UserTemperatureRange(
                                     jsonDecode.data.sevenDaysWeather["$i"]!!.temperatureRange.low,
                                     jsonDecode.data.sevenDaysWeather["$i"]!!.temperatureRange.low
                                 )
                             )
                         }
//                        sevenDaysWeather.add(sevenDaysWeatherItem)
                    Log.d("ApiGET", addSevenDaysWeatherItem["2"]!!.weatherType.toString())
//                    Log.d("ApiGET","size----${sevenDaysWeather.size}")
//tes
                    //判断初始数据有没有相同的
                    if (wListData.all { it.text != city }) {
                       //将7日温度数据添加到一个总的集合
                        sevenDaysWeather.put("$city",addSevenDaysWeatherItem)
                        //设置列表到数据不超过8个
                        if (wListData.size < 8) {
                            //添加数据   给索引测试7日温度数据的添加情况
                            wListData.add(
                                WeatherListData(
                                    city,
                                    "${jsonDecode.data.nowTemperature}",
                                    jsonDecode.data.nowWeatherType,
                                    weatherImage[jsonDecode.data.nowWeatherType] as Int,
                                    sevenDaysWeather["$city"]!!
                                )
                            )
                        }else{
                            Looper.prepare()
                            Toast.makeText(
                                context,
                                "列表已有有8个城市，达到添加最大值",
                                Toast.LENGTH_SHORT
                            ).show()
                            Looper.loop()
                        }
                    }else{
                        Looper.prepare()
                        Toast.makeText(
                            context,
                            "列表已有相同的城市",
                            Toast.LENGTH_SHORT
                        ).show()
                        Looper.loop()
                    }



/*//                    wListData.forEach {
//                        if (it.text != city){
//                            wListData.add(
//                                WeatherListData(
//                                    city,
//                                    "${jsonDecode.data.nowTemperature}°C",
//                                    jsonDecode.data.nowWeatherType,
//                                    weatherImage[jsonDecode.data.nowWeatherType] as Int
//                                )
//                            )
//                        }
//                    }*/


                    //切回主线程
                   /* requireActivity().runOnUiThread {

                    }*/
                    //tes
                    CoroutineScope(Dispatchers.Main).launch {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        Log.d("TAGsss",addFlag.toString())
    }



    /*
 *    var conn : HttpURLConnection? = null
     var uriString = "http://175.178.70.219:8083/api/place/province"
     GlobalScope.async {
         conn = URL(uriString).openConnection() as HttpURLConnection
         conn!!.connect()
         conn!!.inputStream.use { input ->
             var data = input.bufferedReader().readText()
             var jsonDecode = Gson().fromJson(data,Person::class.java)
             provinceNameList = jsonDecode.data // 获取前三的省名
         }
     }
 * */

    /*fun getWeather() {
        val uriString = "http://175.178.70.219:8083/api/weather/temperature"
        thread {
            val uri = URL(uriString)
            val conn = uri.openConnection() as HttpURLConnection
            conn.apply {
                requestMethod = "POST"
                useCaches = false
                doInput =  true
                doOutput = true

                connectTimeout = 3000
            }
            val map = mapOf("city" to "广州", "day" to 2)
            val json = Gson().toJson(map)

            PrintWriter(conn.outputStream).use {
                print(json)

                val message = conn.responseMessage
                val code = conn.responseCode
                Log.d("TAG", "code $code, message $message")
            }
        }
    }*/
}






















/*
class addFragment : Fragment() {
    private var _bd : FragmentAddBinding? = null
    private val bd : FragmentAddBinding get() = _bd!!

    var citiesFlag by Delegates.notNull<Int>();
    private var tempArray : ArrayList<ArrayList<String>> = ArrayList()
    var tempArrays:ArrayList<String> = ArrayList()
    //获取API
    init {

*/
/*        GlobalScope.async {
            println("add Page ----\n${provinceNameList}")
            var mAddAdapter = addAdapter { item, pos ->
                Log.d("TAG", item)
                Log.d("TAG", pos.toString())
//                getCitiesData(item)
            }
            mAddAdapter.submitList(provinceNameList)
            bd.fAddProvince.adapter = mAddAdapter
        }*//*

//            getCitiesData(provinceNameList.first())
*/
/*        var conn : HttpURLConnection? = null
        var provinceUriString = "http://175.178.70.219:8083/api/place/province"
        GlobalScope.async {
            conn = URL(provinceUriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data,Person::class.java)
                provinceNameList = jsonDecode.data

                var mAddAdapter = addAdapter { item, pos ->
                    Log.d("TAG", item)
                    Log.d("TAG", pos.toString())
//                    getCitiesData(item)
                }
                mAddAdapter.submitList(provinceNameList)
                bd.fAddProvince.adapter = mAddAdapter

                println("------\n\n\n\n$provinceNameList")
            }
        }*//*

    }

    */
/*fun getProvinceData(){
        var conn : HttpURLConnection? = null
        var provinceUriString = "http://175.178.70.219:8083/api/place/province"
        GlobalScope.async {
            conn = URL(provinceUriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data,Person::class.java)
                provinceNameList = jsonDecode.data

                var mAddAdapter = addAdapter { item, pos ->
                    Log.d("TAG", item)
                    Log.d("TAG", pos.toString())
//                    getCitiesData(item)
                }
                mAddAdapter.submitList(provinceNameList)
                bd.fAddProvince.adapter = mAddAdapter

                println("------\n\n\n\n$provinceNameList")
            }
        }
    }*//*


*/
/*    fun getCitiesData(provinceName:String){
//        var conn : HttpURLConnection? = null
//        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=$provinceName"
//        GlobalScope.async {
//
//            conn = URL(citiesUriString).openConnection() as HttpURLConnection
//            conn!!.connect()
//            conn!!.inputStream.use { input ->
//                var data = input.bufferedReader().readText()
//                var jsonDecode = Gson().fromJson(data,Person::class.java)
//                citiesFlag = jsonDecode.code
//                citiesNameList = jsonDecode.data
//                var mAddAdapter: addAdapter = addAdapter { item, pos ->
//                    Log.d("TAG", "------$item")
//                    Log.d("TAG", pos.toString())
////                    getCitiesData(item)
//                }
//                mAddAdapter.submitList(citiesNameList)
//                bd.fAddCities.adapter = mAddAdapter
//
//            }
//        }

        var citiesConn : HttpURLConnection? = null
        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=${provinceName}"
        GlobalScope.async {
            citiesConn = URL(citiesUriString).openConnection() as HttpURLConnection
            citiesConn!!.connect()
            citiesConn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data,Person::class.java)
                citiesNameList = jsonDecode.data
                citiesNameList.forEach { i ->
                    println(i)
                }
                var mAddAdapter: addAdapter = addAdapter { item, pos ->
                    Log.d("TAG", "------$item")
                    Log.d("TAG", pos.toString())
//                    getCitiesData(item)
                }
                mAddAdapter.submitList(citiesNameList)
                bd.fAddCities.adapter = mAddAdapter
            }
        }
    }*//*

@OptIn(DelicateCoroutinesApi::class)
fun getCitiesData(provinceNames:String) {
        var conn : HttpURLConnection? = null
        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=$provinceNames"
        */
/*synchronized(this){
            Log.d("TAG--",provinceName)
            conn = URL(citiesUriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data, Person::class.java)
                citiesFlag = jsonDecode.code
                citiesNameList = jsonDecode.data
//                println("citiesNameList=====$citiesNameList")
//                var tempArrayItem: ArrayList<String> = ArrayList<String>()
//                citiesNameList.forEach {
//                    println("---it---$it")
////                    if (citiesNameList.count() == 1){
////                        //子项最小三个才显示，不然会报错
////                        tempArrayItem.add(it)
////                        tempArrayItem.add("")
////                        tempArrayItem.add("")
////                    }else {
////                        tempArrayItem.add(it)
////                    }
////                    tempArrayItem.add(it)
//                    println("citiesNameList.size-------${citiesNameList.size == 1}")
////                    println("tempArrayItem${tempArrayItem}")
//                }
//                tempArrayItem =
//                citiesNameList.add(0,"t")
//                tempArray.add(citiesNameList)
                Log.d("TAGcities", citiesNameList.toString())
//                Log.d("TAG", tempArray.toString())

//                Log.d("TEMP","${tempArrayItem}")

            }
        }*//*

    var temp = provinceNames

    */
/*    Thread(
            Runnable {
                run {
                    provinceNameList.forEach {

                        if (it == temp){
                            tempArrays.add(temp)
                            Log.d("TAGtemp--","$it----${it == temp}----${tempArrays}")

                        }

                    }
                    Log.d("TAG3--",provinceNames)
                    conn = URL(citiesUriString).openConnection() as HttpURLConnection
                    conn!!.connect()
                    conn!!.inputStream.use {
                        var data = it.bufferedReader().readText()
                        var jsonDecode = Gson().fromJson(data, Person::class.java)
                        citiesFlag = jsonDecode.code
                        citiesNameList = jsonDecode.data
                        Log.d("TAGc", provinceName)
                        tempArray.add(citiesNameList)
//                        Log.d("TAGcities", citiesNameList.toString())
                    }
                }
            }
        ).start()*//*

        */
/*GlobalScope.async {
            Log.d("TAG--",provinceName)

            conn = URL(citiesUriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data, Person::class.java)
                citiesFlag = jsonDecode.code
                citiesNameList = jsonDecode.data
//                println("citiesNameList=====$citiesNameList")
//                var tempArrayItem: ArrayList<String> = ArrayList<String>()
//                citiesNameList.forEach {
//                    println("---it---$it")
////                    if (citiesNameList.count() == 1){
////                        //子项最小三个才显示，不然会报错
////                        tempArrayItem.add(it)
////                        tempArrayItem.add("")
////                        tempArrayItem.add("")
////                    }else {
////                        tempArrayItem.add(it)
////                    }
////                    tempArrayItem.add(it)
//                    println("citiesNameList.size-------${citiesNameList.size == 1}")
////                    println("tempArrayItem${tempArrayItem}")
//                }
//                tempArrayItem =
//                citiesNameList.add(0,"t")
                tempArray.add(citiesNameList)
                Log.d("TAGcities", citiesNameList.toString())
//                Log.d("TAG", tempArray.toString())

//                Log.d("TEMP","${tempArrayItem}")

            }
        }*//*

    GlobalScope.async {
        Log.d("TAG--",provinceNames)
        provinceNameList.forEach {



            conn = URL(citiesUriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                if (it == temp){
                    tempArrays.add(temp)
                    Log.d("temp--",temp)
                    Log.d("TAGtemp--","$it----${it == temp}----${tempArrays}")
                    var data = input.bufferedReader().readText()
                    var jsonDecode = Gson().fromJson(data, Person::class.java)
                    citiesFlag = jsonDecode.code
                    citiesNameList = jsonDecode.data
//zhang zhou
                    tempArray.add(citiesNameList)
                    Log.d("TAGcities", citiesNameList.toString())
                }


            }
        }

    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        */
/*bd.fAddProvince.setOnItemClickListener {parent, view, position, id ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
        bd.fAddProvince.*//*

        println("cities$provinceName")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bd = FragmentAddBinding.inflate(inflater,container,false)
*/
/*        GlobalScope.async {
            println("add Page ----\n${provinceNameList}----")
            var mAddAdapter = addAdapter { item, pos ->
                Log.d("TAG", item)
                Log.d("TAG", pos.toString())
                provinceName=item
            }
            mAddAdapter.submitList(provinceNameList)
            bd.fAddProvince.adapter = mAddAdapter
            println("cities$provinceName")
            getCitiesData(provinceName)
        }*//*

        ExpandableListView()
        return bd.root
    }

    private fun ExpandableListView() {
        provinceNameList.forEach {
            Log.d("TAG",it)

            getCitiesData(it)
//            tempArray.add(citiesNameList)

        }

        var adapter = ExpandableListViewAdapter(provinceNameList, tempArray)

        bd.expandableList.setAdapter(adapter)

        bd.expandableList.setOnGroupClickListener { parent, v, groupPosition, id -> false }
        bd.expandableList.setOnChildClickListener { parent, v, groupPosition, childPosition, id -> false }
    }


}*/