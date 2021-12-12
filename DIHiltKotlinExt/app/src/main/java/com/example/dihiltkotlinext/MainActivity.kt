package com.example.dihiltkotlinext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.example.dihiltkotlinext.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("result: MainActivity Memanggil ${viewModel.getNilai()} ")

        binding.text1.text = "From Activity ${viewModel.getNilai()}"

        val fragmentManager = supportFragmentManager

        fragmentManager
                .beginTransaction()
                .replace(R.id.container_create_note, BlankFragment() )
                .commit()

    }
}