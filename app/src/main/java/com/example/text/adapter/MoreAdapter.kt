package com.example.text.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.text.databinding.ItemListMoreBinding
import com.example.text.network.WeatherListData

class MoreAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var _data: MutableList<WeatherListData>? = null
    private val data: List<WeatherListData> get() = _data!!

//  接收传进来的列表数据
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: MutableList<WeatherListData>) {
        this._data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemListMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherListData = data[position]
        //设置对应的数据
        holder.bd.iMoreText.text = weatherListData.text //城市名
        holder.bd.iMoreTemperature.text = "${weatherListData.temperature}°C" //温度
        holder.bd.iMoreWeather.text = weatherListData.weather //天气
        holder.bd.iMoreImage.setImageResource(weatherListData.weatherImage) //天气图标
    }

    override fun getItemCount() = if (_data == null) 0 else data.size
}


class MyViewHolder(val bd: ItemListMoreBinding) : RecyclerView.ViewHolder(bd.root)