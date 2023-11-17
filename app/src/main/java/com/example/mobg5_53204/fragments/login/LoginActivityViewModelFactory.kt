package com.example.mobg5_53204.fragments.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//responsible for instantiating the ViewModel object
class LoginActivityViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginActivityViewModel::class.java)) {
            return LoginActivityViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}