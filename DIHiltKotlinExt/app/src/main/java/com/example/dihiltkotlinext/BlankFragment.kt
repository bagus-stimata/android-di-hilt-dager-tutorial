package com.example.dihiltkotlinext

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.dihiltkotlinext.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : Fragment() {
    val viewModel: MainViewModel by viewModels()

    lateinit var binding: FragmentBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_blank, container, false)
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("result: Blank Fragment Memanggil ${viewModel.getNilai2()} ")
        binding.text1.text = "From Fragment ${viewModel.getNilai()}"
    }
}