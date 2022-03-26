package com.example.samplemvvm.ui.frag

import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseActivity
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.ActivityFragBinding
import org.koin.android.ext.android.inject

class FragActivity : BaseActivity<ActivityFragBinding, FragViewModel>() {


    override val layoutResourceId: Int = R.layout.activity_frag
    override val viewModelFactory: ViewModelProvider.Factory by inject()
    override val viewModel: FragViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FragViewModel::class.java]
    }

    override fun initStartView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FirstFragment.newInstance())
            .commitNow()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnOne.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FirstFragment.newInstance())
                .commitNow()
        }

        binding.btnTwo.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TwoFragment.newInstance())
                .commitNow()
        }
    }
}