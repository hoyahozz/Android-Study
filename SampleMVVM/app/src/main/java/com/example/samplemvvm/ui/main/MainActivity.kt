package com.example.samplemvvm.ui.main

import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvm.BaseActivity
import com.example.samplemvvm.BuildConfig
import com.example.samplemvvm.R
import com.example.samplemvvm.adapter.RecyclerViewDecoration
import com.example.samplemvvm.adapter.StationAdapter
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.data.remote.entity.Row
import com.example.samplemvvm.databinding.ActivityMainBinding
import com.example.samplemvvm.ui.frag.FragActivity
import com.example.samplemvvm.ui.user.UserActivity
import com.example.samplemvvm.util.log
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainClickListener {

    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModelFactory: ViewModelProvider.Factory by inject()
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var adapter: StationAdapter

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    override fun initStartView() {
        log("MainActivity Create")
        adapter = StationAdapter().apply { setItemLongClickListener(this@MainActivity) }

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = this@MainActivity.adapter
            this.addItemDecoration(RecyclerViewDecoration(10))
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.getList(API_KEY)

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

    override fun initAfterBinding() {
        binding.btnRoom.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        binding.btn.setOnClickListener {
            val intent = Intent(this, FragActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onListItemClick(item: Row) {
        Toast.makeText(this, "${item.station_name} 데이터베이스 저장 완료", Toast.LENGTH_SHORT).show()
        viewModel.insertFavorite(UserEntity(null, item.station_name))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
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