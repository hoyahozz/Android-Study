package com.example.samplemvvm.ui.frag

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.BaseFragment
import com.example.samplemvvm.R
import com.example.samplemvvm.databinding.FragmentFirstBinding
import com.example.samplemvvm.util.log
import org.koin.android.ext.android.inject

class FirstFragment : BaseFragment<FragmentFirstBinding, FragViewModel>() {

    companion object {
        fun newInstance(): FirstFragment{
            val args = Bundle()

            val fragment = FirstFragment()
            fragment.arguments = args
            return fragment
        }

        private val TAG = "FirstFragment"
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
        }

        viewModel.count.observe(this) {
            log("observing")
            binding.count.text = viewModel.count.value.toString()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy()")
    }

}