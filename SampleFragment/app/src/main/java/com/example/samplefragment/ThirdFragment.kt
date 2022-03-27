package com.example.samplefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.samplefragment.databinding.FragmentFirstBinding
import com.example.samplefragment.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding : FragmentThirdBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): ThirdFragment{
            val args = Bundle()

            val fragment = ThirdFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModelFactory = ViewModelProvider.NewInstanceFactory()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.count.observe(viewLifecycleOwner) {
            binding.count.text = it.toString()
        }

        binding.plus.setOnClickListener {
            viewModel.getUpdatedCount(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}