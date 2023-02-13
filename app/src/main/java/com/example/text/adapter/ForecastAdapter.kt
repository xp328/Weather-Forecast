package com.example.text.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.text.databinding.ItemListForecastBinding
import com.example.text.network.UserSevenDaysWeatherInt
import com.example.text.network.weatherImage
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ForecastHolder>() {
    private var _data: Map<String, UserSevenDaysWeatherInt>? = null
    private val data: Map<String, UserSevenDaysWeatherInt> get() = _data!!

    //接收传进来的7日温度数据
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: Map<String, UserSevenDaysWeatherInt>) {
        this._data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            ItemListForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        val weatherListData = data["$position"]

        //时间
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM月dd日")
        val formatted = current.format(formatter)

        //7日数据添加
        val sevenDaysCalendar = listOf<Calendar>(
            Calendar.getInstance(),
            Calendar.getInstance().apply { add(Calendar.DATE,1) },
            Calendar.getInstance().apply { add(Calendar.DATE,2) },
            Calendar.getInstance().apply { add(Calendar.DATE,3) },
            Calendar.getInstance().apply { add(Calendar.DATE,4) },
            Calendar.getInstance().apply { add(Calendar.DATE,5) },
            Calendar.getInstance().apply { add(Calendar.DATE,6) },
        )
        val simpleFormatter = SimpleDateFormat("MM月dd日",Locale.CHINA)


        //设置页面对应对数据
        holder.bd.itemForecastTime.text = simpleFormatter.format(sevenDaysCalendar[position].time)
        if (position == 0) {
            holder.bd.itemForecastDate.text = "今日"
        }

        holder.bd.itemForecastImage.setImageResource(weatherImage[weatherListData!!.weatherType] as Int)
        holder.bd.itemForecastText.text = weatherListData.weatherType
        holder.bd.itemForecastLow.text = "${weatherListData.temperatureRange.low}°C"
        holder.bd.itemForecastHigh.text = "${weatherListData.temperatureRange.high}°C"

    }

    override fun getItemCount() = if (_data == null) 0 else data.size
}

class ForecastHolder(val bd: ItemListForecastBinding) : RecyclerView.ViewHolder(bd.root)