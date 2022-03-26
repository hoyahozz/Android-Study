package com.example.samplemvvm.ui.frag

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseFragment
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.FragmentTwoBinding
import org.koin.android.ext.android.inject

class TwoFragment : BaseFragment<FragmentTwoBinding, FragViewModel>() {

    companion object {
        fun newInstance(): TwoFragment{
            val args = Bundle()

            val fragment = TwoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutResourceId = R.layout.fragment_two

    private val viewModelFactory: ViewModelProvider.Factory by inject()

    override val viewModel: FragViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[FragViewModel::class.java]
    }

    override fun initStartView() {
        binding.count.text = viewModel.count.value.toString()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

        binding.btnPlus.setOnClickListener {
            viewModel.getUpdatedCount(1)
            binding.count.text = viewModel.count.value.toString()
        }
    }


}