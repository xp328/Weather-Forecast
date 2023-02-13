package com.example.text.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.text.adapter.ExpandableListViewAdapter
import com.example.text.databinding.FragmentAddBinding
import com.example.text.network.*
import com.example.text.network.OkHttpUtils.BASE_URL
import com.example.text.network.OkHttpUtils.gson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread



class AddFragment : Fragment() {
    private var _bd: FragmentAddBinding? = null
    private val bd: FragmentAddBinding get() = _bd!!

    var text: String = ""//右上角的文字


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bd = FragmentAddBinding.inflate(inflater, container, false)

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

        //设置二级列表ExpandableListView对应的Adapter
        val adapter = ExpandableListViewAdapter(provinceNameList, allCitiesNameArray)

        bd.expandableList.setAdapter(adapter)

        //一级列表对应的点击事件
        bd.expandableList.setOnGroupClickListener { parent, v, groupPosition, id ->
            false
        }

        //二级列表对应的点击事件
        bd.expandableList.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            bd.iAddNextText.text = allCitiesNameArray[groupPosition][childPosition]
            text = allCitiesNameArray[groupPosition][childPosition]
            false
        }
    }


    //ApiPost
    fun addGetWeather(city: String) {
        val uriString = "http://$BASE_URL/api/weather/today"

        lifecycleScope.launch(context = Dispatchers.IO) {
            try {
                val map = mapOf("city" to city)
                val json = Gson().toJson(map)
                val body = json.toRequestBody()

                val response = OkHttpUtils.handle(uriString, OkHttpUtils.POST, body)
                if (response.code == 200) {
                    val responseBodyString = response.body?.string() //buyaoyong toString()
                    var personWeather = gson.fromJson(responseBodyString, PersonWeather::class.java)

                    //添加7日温度数据
                     var addSevenDaysWeatherItem: MutableMap<String,UserSevenDaysWeatherInt> = mutableMapOf()
                     for (i in 0 until personWeather.data.sevenDaysWeather.size){
                         addSevenDaysWeatherItem["$i"] = UserSevenDaysWeatherInt(
                             personWeather.data.sevenDaysWeather["$i"]!!.weatherType,
                             UserTemperatureRange(
                                 personWeather.data.sevenDaysWeather["$i"]!!.temperatureRange.low,
                                 personWeather.data.sevenDaysWeather["$i"]!!.temperatureRange.low
                             )
                         )
                     }

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
                                    "${personWeather.data.nowTemperature}",
                                    personWeather.data.nowWeatherType,
                                    weatherImage[personWeather.data.nowWeatherType] as Int,
                                    sevenDaysWeather["$city"]!!
                                )
                            )
                        }else{
                            Looper.prepare()
                            Toast.makeText(
                                context,
                                "列表已有8个城市，达到添加最大值",
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

                    CoroutineScope(Dispatchers.Main).launch {
                        findNavController().popBackStack()
                    }

                }else{
                    val responseBodyString = response.body?.string()
                    Log.e("TEST", "CoroutineError: $responseBodyString" )
                }
            } catch (e: Exception){
                Log.e(TAG, "Error: "    ,e )
            }

        }
    }
}
