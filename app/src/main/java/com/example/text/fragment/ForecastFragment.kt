package com.example.text.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.text.R
import com.example.text.adapter.ForecastAdapter
import com.example.text.databinding.FragmentForecastBinding
import com.example.text.network.*

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import kotlin.properties.Delegates

private const val uriString = "http://175.178.70.219:8083/api/place/province"
@RequiresApi(Build.VERSION_CODES.O)
class ForecastFragment : Fragment() {

    private var _bd: FragmentForecastBinding? = null
    private val bd: FragmentForecastBinding get() = _bd!!


    //获取API
    //第一个页面的init方法 确保应用一开始就获取省名
    init {
        lifecycleScope.launch(context = Dispatchers.IO) {
                val response = OkHttpUtils.handle(uriString, OkHttpUtils.GET, null)
                if (response.code == 200) {
                    val responseBodyString = response.body?.string() //buyaoyong toString()
                    val gson = Gson()
                    val responseData: MyApiResponseEntity<List<String>> = gson.fromJson(
                        responseBodyString,
                        object : TypeToken<MyApiResponseEntity<List<String>>>() {}.type
                    )
                    provinceNameList = responseData.data
                    provinceNameList.forEach {
                        getCitiesData(it)
                    }
                }else{
                    val responseBodyString = response.body?.string()
                    Log.e("TEST", "CoroutineError: ${responseBodyString}" )
                }
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
                    //在use里执行的顺序会混乱 通过if判断
                    if (it == temp) {
                        Log.d("ITTEMP","$it----$temp")
                        var data = input.bufferedReader().readText()
                        var jsonDecode = Gson().fromJson(data, Person::class.java)
                        citiesNameList = jsonDecode.data
                        tempArray.add(citiesNameList)
                    }
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("onCreate---test")
        _bd = FragmentForecastBinding.inflate(inflater, container, false)

        //将按钮的层级调高
        //由于布局顺序关系，如果不设置按钮的层级，顺序是从右到左的层叠的关系，实际需要的顺序是从左到右的层叠的关系
        adjustButton(bd.fForeCityButtonTwo)
        adjustButton(bd.fForeCityButton)

        //设置顶部按钮文字
        bd.fForeCityButton.text = wListData[0].text
        bd.fForeCityButtonTwo.text = wListData[1].text
        bd.fForeCityButtonThree.text = wListData[2].text

        //设置详细信息里的城市
        bd.fForeCityText.text = wListData[0].text


        //设置7日温度RecyclerView对应的Adapter
        var ForecastAdapter = ForecastAdapter()
        //通过sevenDaysWeather：map里到key值拿对应的7日温度数据
        ForecastAdapter.submitList(sevenDaysWeather[wListData[0].text]!!)
        bd.fForeRecyclerView.adapter = ForecastAdapter

        return bd.root
    }

    //将按钮的层级调高
    private fun adjustButton(button: Button) {
        var params = button.layoutParams
        bd.fForeConstraintLayout.removeView(button)
        bd.fForeConstraintLayout.addView(button, params)
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //跳转页面
        bd.fForeMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_moreFragment)
        }

        var number = 0

        val fTabOnClickListener = View.OnClickListener { p0 ->
            when(p0?.id){
                bd.fForeCityButton.id -> number = 0
                bd.fForeCityButtonTwo.id -> number = 1
                bd.fForeCityButtonThree.id -> number = 2
            }
            setfragmentData(number)
        }

        bd.fForeCityButton.setOnClickListener(fTabOnClickListener)
        bd.fForeCityButtonTwo.setOnClickListener(fTabOnClickListener)
        bd.fForeCityButtonThree.setOnClickListener(fTabOnClickListener)



        val temperatureOrigin = wListData[number].temperature.toInt()
        var isFahrenheit: Boolean by Delegates.observable(false) {
                _, oldValue, newValue ->
            if (newValue == false) bd.fForeTemperature.text = temperatureOrigin.toString()
            else {
                val fah = (temperatureOrigin * 9 / 5) + 32
                bd.fForeTemperature.text = fah.toString()
            }
        }

        //华氏度
        bd.fForeFahrenheit.setOnClickListener {
            isFahrenheit = true
        }

        //摄氏度
        bd.fForeCentigrade.setOnClickListener {
            isFahrenheit = false
        }
    }

    private fun setfragmentData(number: Int) {
        bd.fForeCityText.text = wListData[number].text
        bd.fForeTemperature.text = wListData[number].temperature
        bd.fForeImageView.setImageResource(weatherImage[wListData[number].weather] as Int)
        val forecastAdapter = ForecastAdapter()
        forecastAdapter.submitList(sevenDaysWeather[wListData[number].text]!!)
        bd.fForeRecyclerView.adapter = forecastAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _bd = null
    }

}