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
import com.example.samplemvvm.databinding.FragmentSecondBinding
import com.example.samplemvvm.util.log
import org.koin.android.ext.android.inject

class SecondFragment : BaseFragment<FragmentSecondBinding, FragViewModel>() {

    companion object {
        fun newInstance(): SecondFragment{
            val args = Bundle()

            val fragment = SecondFragment()
            fragment.arguments = args
            return fragment
        }
        private val TAG = "SecondFragment"
    }

    override val layoutResourceId = R.layout.fragment_second

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach()")
    }


}