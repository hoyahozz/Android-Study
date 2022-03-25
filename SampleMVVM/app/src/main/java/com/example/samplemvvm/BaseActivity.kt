package com.example.samplemvvm

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.R
import com.example.samplemvvm.util.log

abstract class BaseActivity<T: ViewDataBinding, R: ViewModel> : AppCompatActivity() {

    lateinit var binding: T

    @get:LayoutRes
    abstract val layoutResourceId: Int
    abstract val viewModel: R
    abstract val viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        setContentView(binding.root)

        if(com.example.samplemvvm.R.id.toolbar != null) {
            log("toolbar ON")
            setSupportActionBar(findViewById(com.example.samplemvvm.R.id.toolbar))
            supportActionBar!!.setHomeAsUpIndicator(com.example.samplemvvm.R.drawable.ic_back)
        }


//        setSupportActionBar(binding.toolbar)

        log("BaseActivity Create")

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰.
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}