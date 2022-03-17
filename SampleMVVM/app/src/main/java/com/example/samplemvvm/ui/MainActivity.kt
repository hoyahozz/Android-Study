package com.example.samplemvvm.ui

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.samplemvvm.BuildConfig
import com.example.samplemvvm.data.RetrofitObject
import com.example.samplemvvm.data.StationApi
import com.example.samplemvvm.data.model.Station
import com.example.samplemvvm.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* With Activity
        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(StationApi.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val retrofitApi = retrofit.create(StationApi::class.java)

        retrofitApi.getList(API_KEY)?.enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                Toast.makeText(this@MainActivity, "zz", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "not zz", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        }) */

        /* With Object

        val callList = RetrofitObject.api.getList(API_KEY)

        callList?.enqueue(object : Callback<Station> {
            override fun onResponse(call: Call<Station>, response: Response<Station>) {
                Toast.makeText(this@MainActivity, "${response.body()!!.subwayStationMaster.listTotalCount}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Station>, t: Throwable) {
                Toast.makeText(this@MainActivity, "not zz", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        }) */



        /* With Interface */
        val retrofitApi = StationApi.create()

        retrofitApi.getList(API_KEY)?.enqueue(object : Callback<Station> {
            override fun onResponse(call: Call<Station>, response: Response<Station>) {
                Toast.makeText(this@MainActivity, "${response.body()!!.subwayStationMaster.listTotalCount}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Station>, t: Throwable) {
                Toast.makeText(this@MainActivity, "cannot", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}
