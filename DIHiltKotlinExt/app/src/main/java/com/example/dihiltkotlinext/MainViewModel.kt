package com.example.dihiltkotlinext

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class MainViewModel @ViewModelInject constructor(private val str: String): ViewModel() {


    fun getNilai():String{
        return "Aku dipanggil ${str} "
    }
    fun getNilai2():String{
        return "Fragment Yes ${str} "
    }

}

@Module
@InstallIn(SingletonComponent::class)
class ModelModule {
    @Singleton
    @Provides
    fun privideStringle(): String{
        return "###AND Provide Some String BOS gini"
    }

}