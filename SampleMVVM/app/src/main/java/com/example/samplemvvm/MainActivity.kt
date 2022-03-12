package com.example.samplemvvm

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.dongyang.android.aob.Map.Model.Bike.RentBikeStatus
import com.dongyang.android.aob.Map.Model.Bike.SeoulBike
import com.dongyang.android.aob.Map.Service.BikeService
import com.example.samplemvvm.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val SEOUL_API_KEY = "hideonbush"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BikeService.SEOUL_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: BikeService = retrofit.create(BikeService::class.java)

        retrofitAPI.getBike(SEOUL_API_KEY, 1, 1000)?.enqueue(object : Callback<SeoulBike?> {
            override fun onResponse(call: Call<SeoulBike?>?, response: Response<SeoulBike?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val rentBikeStatus: RentBikeStatus? = data!!.rentBikeStatus
                }
            }

            override fun onFailure(call: Call<SeoulBike?>?, t: Throwable) {
                t.printStackTrace()
            }
        })


    }
}