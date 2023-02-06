package com.example.text.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.text.databinding.ItemListMoreBinding
import com.example.text.network.WeatherListData

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var _data: MutableList<WeatherListData>? = null
    private val data: List<WeatherListData> get() = _data!!

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: MutableList<WeatherListData>) {
        this._data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemListMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherListData = data[position]
        holder.bd.iMoreText.text = weatherListData.text
        holder.bd.iMoreWeather.text = weatherListData.weather
        holder.bd.iMoreTemperature.text = "${weatherListData.temperature}°C"
        holder.bd.iMoreImage.setImageResource(weatherListData.weatherImage)
    }

    override fun getItemCount() = if (_data == null) 0 else data.size
}


class MyViewHolder(val bd: ItemListMoreBinding) : RecyclerView.ViewHolder(bd.root)