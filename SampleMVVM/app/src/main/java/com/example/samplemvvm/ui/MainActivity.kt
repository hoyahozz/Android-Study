package com.example.samplemvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvm.BuildConfig
import com.example.samplemvvm.MyApplication
import com.example.samplemvvm.adapter.RecyclerViewDecoration
import com.example.samplemvvm.adapter.StationAdapter
import com.example.samplemvvm.config.ViewModelFactory
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.data.remote.entity.Row
import com.example.samplemvvm.data.remote.repository.StationRepository
import com.example.samplemvvm.databinding.ActivityMainBinding
import com.example.samplemvvm.util.log
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val viewModelFactory: ViewModelProvider.Factory by inject()

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log("MainActivity Create")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationRepository = StationRepository()
        val userRepository = MyApplication.getRepository()

        val viewModelFactory = ViewModelFactory(stationRepository, userRepository)
        val adapter = StationAdapter().apply { setItemLongClickListener(this@MainActivity) }
//        val adapter = StationAdapter()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getList(API_KEY)

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapter
            this.addItemDecoration(RecyclerViewDecoration(10))
        }

        viewModel.station.observe(this) {
            if (it.isSuccessful) {
                val row = it.body()!!.subwayStationMaster.row
                adapter.submitList(row)
                log("Connect!")
            } else {
                log("Can not connect!")
            }
        }
    }

    override fun onListItemClick(item: Row) {
        Toast.makeText(this, "${item.station_name} 데이터베이스 저장 완료", Toast.LENGTH_SHORT).show()
        viewModel.insertFavorite(UserEntity(item.station_name))
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