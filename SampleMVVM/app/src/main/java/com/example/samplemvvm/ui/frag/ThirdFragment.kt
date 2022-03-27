package com.example.samplemvvm.ui.frag

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseFragment
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.FragmentThirdBinding
import org.koin.android.ext.android.inject

class ThirdFragment : BaseFragment<FragmentThirdBinding, FragViewModel>() {

    companion object {
        fun newInstance(): ThirdFragment{
            val args = Bundle()

            val fragment = ThirdFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutResourceId: Int = R.layout.fragment_third
    private val viewModelFactory : ViewModelProvider.Factory by inject()

    override val viewModel: FragViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FragViewModel::class.java]
    }

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.count.text = viewModel.count.value.toString()
        binding.btnPlus.setOnClickListener {
            viewModel.getUpdatedCount(1)
            binding.count.text = viewModel.count.value.toString()
        }
    }


}