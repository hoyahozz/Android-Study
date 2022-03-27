package com.example.samplemvvm.ui.frag

import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseActivity
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.ActivityFragBinding
import org.koin.android.ext.android.inject

class FragActivity : BaseActivity<ActivityFragBinding, FragViewModel>() {

    companion object {
        private val TAG = "Activity"
    }
    
    override val layoutResourceId: Int = R.layout.activity_frag
    override val viewModelFactory: ViewModelProvider.Factory by inject()
    override val viewModel: FragViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FragViewModel::class.java]
    }

    private val manager = supportFragmentManager


    override fun initStartView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FirstFragment.newInstance())
            .commitNow()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnOne.setOnClickListener {
            val transaction = manager.beginTransaction()
            transaction
                .replace(R.id.container, FirstFragment.newInstance())
                .commit()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, FirstFragment.newInstance())
//                .commitNow()
        }

        binding.btnTwo.setOnClickListener {
            val transaction = manager.beginTransaction()
            transaction
                .replace(R.id.container, SecondFragment.newInstance())
                .commit()
        }

        binding.btnThird.setOnClickListener {
            val transaction = manager.beginTransaction()
            transaction
                .replace(R.id.container, ThirdFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}