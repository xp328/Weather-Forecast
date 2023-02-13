package com.example.text.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.text.R
import com.example.text.adapter.ForecastAdapter
import com.example.text.databinding.FragmentForecastBinding
import com.example.text.network.*
import com.example.text.network.OkHttpUtils.BASE_URL

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.properties.Delegates

private const val uriString = "http://$BASE_URL/api/place/province"
private const val TAG = "ForecastFragment"

class ForecastFragment : Fragment() {

    private var _bd: FragmentForecastBinding? = null
    private val bd: FragmentForecastBinding get() = _bd!!

    private fun getCitiesData(provinceNames: String){
        val citiesUriString = "http://$BASE_URL/api/place/cities?province=$provinceNames"

        val responses = OkHttpUtils.handle(citiesUriString, OkHttpUtils.GET, null)
        if (responses.code == 200) {
            val responseBodyString = responses.body?.string() //buyaoyong toString()
            val gson = Gson()
            val responseData: MyApiResponseEntity<List<String>> = gson.fromJson(
                responseBodyString,
                object : TypeToken<MyApiResponseEntity<List<String>>>() {}.type
            )
            //保存单个省份的城市名集合
            citiesNameList = responseData.data
            //添加到总的省份城市名集合中
            allCitiesNameArray.add(citiesNameList)
            Log.d("TagCities","$allCitiesNameArray")

        }else{
            val responseBodyString = responses.body?.string()
            Log.e("TEST", "CoroutineError: $responseBodyString" )
        }
    }


    //获取API GET
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(context = Dispatchers.IO) {
            try {
                val response = OkHttpUtils.handle(uriString, OkHttpUtils.GET, null)
                if (response.code == 200) {
                    val responseBodyString = response.body?.string() //buyaoyong toString()
                    val gson = Gson()
                    val responseData: MyApiResponseEntity<List<String>> = gson.fromJson(
                        responseBodyString,
                        object : TypeToken<MyApiResponseEntity<List<String>>>() {}.type
                    )
                    //获取省份
                    provinceNameList = responseData.data
                    provinceNameList.forEach {
                        getCitiesData(it)
                    }
                    Log.d("TagProvince","$provinceNameList")
                }else{
                    val responseBodyString = response.body?.string()
                    Log.e("TEST", "CoroutineError: $responseBodyString" )
                }
            } catch (e:Exception){
                Log.e(TAG, "Error: "    ,e )
            }
        }
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
        val ForecastAdapter = ForecastAdapter()

        //通过sevenDaysWeather：map里到key值拿对应的7日温度数据
        ForecastAdapter.submitList(sevenDaysWeather[wListData[0].text]!!)
        bd.fForeRecyclerView.adapter = ForecastAdapter

        return bd.root
    }

    //将按钮的层级调高
    private fun adjustButton(button: Button) {
        val params = button.layoutParams
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

        //用于切换页面时获取数字
        var number = 0

        //获取对应页面摄氏度
        var temperatureOrigin = wListData[number].temperature.toInt()


        val fTabOnClickListener = View.OnClickListener { p0 ->
            //点击对应的页面更改number
            when(p0?.id){
                bd.fForeCityButton.id -> number = 0
                bd.fForeCityButtonTwo.id -> number = 1
                bd.fForeCityButtonThree.id -> number = 2
            }
            //将更改的数字传入setfragmentData函数
            setfragmentData(number)

            //点击页面的时候把temperatureOrigin更新
            temperatureOrigin = wListData[number].temperature.toInt()
        }

        bd.fForeCityButton.setOnClickListener(fTabOnClickListener)
        bd.fForeCityButtonTwo.setOnClickListener(fTabOnClickListener)
        bd.fForeCityButtonThree.setOnClickListener(fTabOnClickListener)



        //摄氏度和华氏度切换
        var isFahrenheit: Boolean by Delegates.observable(false) {
                _, _, newValue ->
            if (!newValue) bd.fForeTemperature.text = temperatureOrigin.toString()
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

    //根据传入的数字 设置数据
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