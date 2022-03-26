package com.example.samplemvvm.ui.frag

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseFragment
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.FragmentFirstBinding
import org.koin.android.ext.android.inject

class FirstFragment : BaseFragment<FragmentFirstBinding, FragViewModel>() {

    companion object {
        fun newInstance(): FirstFragment{
            val args = Bundle()

            val fragment = FirstFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutResourceId: Int = R.layout.fragment_first
    private val viewModelFactory : ViewModelProvider.Factory by inject()

    override val viewModel: FragViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[FragViewModel::class.java]
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