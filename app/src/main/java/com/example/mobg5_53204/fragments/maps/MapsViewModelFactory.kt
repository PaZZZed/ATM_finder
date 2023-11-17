package com.example.mobg5_53204.fragments.maps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MapsViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}