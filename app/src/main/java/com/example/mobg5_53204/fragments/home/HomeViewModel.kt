package com.example.mobg5_53204.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mobg5_53204.database.api.ATMDataFetcher
import com.example.mobg5_53204.database.api.ATMRecord

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val atmList: LiveData<List<ATMRecord>> = ATMDataFetcher.atmList

    init {
        ATMDataFetcher.fetchATMs()
    }
}
