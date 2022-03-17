package com.example.samplemvvm.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BuildConfig
import com.example.samplemvvm.config.ViewModelFactory
import com.example.samplemvvm.data.repository.StationRepository
import com.example.samplemvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StationViewModel

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = StationRepository()
        val viewModelFactory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StationViewModel::class.java)
        viewModel.getList(API_KEY)

        viewModel.station.observe(this) {
            if(it.isSuccessful) {
                Toast.makeText(this@MainActivity, "${it.body()?.subwayStationMaster!!.listTotalCount}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "not zz", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

    /* With Interface
    val retrofitApi = StationApi.create()

    retrofitApi.getList(API_KEY)?.enqueue(object : Callback<Station> {
        override fun onResponse(call: Call<Station>, response: Response<Station>) {
            Toast.makeText(this@MainActivity, "${response.body()!!.subwayStationMaster.listTotalCount}", Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<Station>, t: Throwable) {
            Toast.makeText(this@MainActivity, "cannot", Toast.LENGTH_SHORT).show()
            t.printStackTrace()
        }
    }) */

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

    val callList = RetrofitObject.api.getList(MainActivity.API_KEY)

    callList?.enqueue(object : Callback<Station> {
        override fun onResponse(call: Call<Station>, response: Response<Station>) {
            Toast.makeText(this@MainActivity, "${response.body()!!.subwayStationMaster.listTotalCount}", Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<Station>, t: Throwable) {
            Toast.makeText(this@MainActivity, "not zz", Toast.LENGTH_SHORT).show()
            t.printStackTrace()
        }
    })

    */