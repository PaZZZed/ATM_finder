package com.example.mobg5_53204.fragments.favoris

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FavorisViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavorisViewModel::class.java)) {
            return FavorisViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}