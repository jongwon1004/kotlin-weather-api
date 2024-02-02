package com.example.kotlin_retfit_sample_weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_retfit_sample_weather.adapter.WeatherAdapter
import com.example.kotlin_retfit_sample_weather.databinding.FragmentWeatherListBinding
import com.example.kotlin_retfit_sample_weather.model.Weather
import com.example.kotlin_retfit_sample_weather.model.WeatherDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherListFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding

    // OpenWeatherMapのAPIアクセスに必要なキー
    private val API_KEY = "003ef1d65597b85d2ab6fa19b59383b6"

    // 対象となる日本の都市のリスト
    private val cities = listOf("Tokyo", "Osaka", "Kyoto", "Hiroshima", "Fukuoka", "Hokkaido", "Okinawa", "Aomori", "Nagano", "Tottori", "Nagoya")

    // 取得した天気情報を保存するためのリスト
    private val weatherList = mutableListOf<Weather>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // このフラグメントのビューが初めて作成されたときに呼ばれる関数
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerViewのデザインを定義するLayoutManagerを設定
        binding.rvCitylist.layoutManager = LinearLayoutManager(context)
        // APIから天気データを取得する関数を呼び出し
        fetchData()
    }


    // OpenWeatherMap APIから天気データを取得する関数
    private fun fetchData() {
        // 既存のリストのデータをクリア
        weatherList.clear()
        var completedRequests = 0  // 成功したAPIリクエストの数をカウントする変数

        // Retrofit インスタンスを作成するためには、まず設定となる Retrofit.Builder クラスを使用します。
        // Retrofitを使用してAPI通信を行う準備
        val retrofit = Retrofit.Builder()

            // retrofit の baseUrl()メソッドを使用して、API のベース URL を設定します。
            // この URL は、後で定義するエンドポイントの URL の基盤となります。
            .baseUrl("https://api.openweathermap.org/")

            // API からのレスポンスデータは通常 JSON 形式です。
            //この JSON データを Java のオブジェクトに変換するために、GsonConverterFactory を追加します。
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val api = retrofit.create(WeatherApi::class.java)

        // 各都市の天気情報をAPIから取得

        // 모든 도시의 날씨 정보를 가져오기 위해 cities 리스트를 순회
        for (city in cities) {
            api.fetchWeather(city, "ja", API_KEY)
                .enqueue(object : Callback<Weather> {
                    override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                        response.body()?.let { weather ->
                            weatherList.add(weather) // 取得した天気情報をリストに追加

                            binding.rvCitylist.adapter?.notifyDataSetChanged() ?: run {
                                binding.rvCitylist.adapter =
                                    WeatherAdapter(weatherList) { selectedWeather ->
                                        // 天気情報をクリックした時の処理
                                        parentFragmentManager.setFragmentResult(
                                            REQUEST_WEATHER_DETAIL,
                                            bundleOf(SELECTED_WEATHER to selectedWeather)
                                        )
                                        // 画面遷移
                                        parentFragmentManager.beginTransaction()
                                            .replace(
                                                R.id.fragmentContainer,
                                                WeatherDetailsFragment()
                                            )
                                            .addToBackStack(null)
                                            .commit()
                                    }
                            }
                        }
                    }

                    override fun onFailure(call: Call<Weather>, t: Throwable) {
                        // エラーログを出力
                        Log.e("API_ERROR", "Failed to fetch weather data for $city", t)
                    }
                })
        }
    }

    companion object {
        val REQUEST_WEATHER_DETAIL = "REQUEST_WEATHER_DETAIL"
        val SELECTED_WEATHER = "SELECTED_WEATHER"
    }


}