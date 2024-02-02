package com.example.kotlin_retfit_sample_weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_retfit_sample_weather.databinding.WeatherItemBinding
import com.example.kotlin_retfit_sample_weather.model.Weather
import kotlin.math.roundToInt

class WeatherAdapter(
    private val weatherList:List<Weather>,
    private val onWeatherClicked: (Weather) -> Unit
):RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(private val binding:WeatherItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(weather:Weather){
            binding.tvCityName.text = weather.name
            binding.tvWeatherDescription.text = weather.weather[0].description
            binding.tvTemperature.text = "${(weather.main.temp-273.15).roundToInt()}℃"
            binding.root.setOnClickListener {
                onWeatherClicked(weather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        // 各アイテムのレイアウトを指定して、レイアウトをバインドする。
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // WeatherViewHolderのインスタンスを作成して返す。
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather : Weather = weatherList[position]
        holder.bind(weather)
    }
}