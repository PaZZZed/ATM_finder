package com.example.mobg5_53204.fragments.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobg5_53204.database.api.ATMDataFetcher
import com.example.mobg5_53204.database.api.ATMRecord

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val _atmRecordList =
        MutableLiveData<List<ATMRecord>>()  // Change ATMFields to ATMRecord
    val atmRecordList: LiveData<List<ATMRecord>> get() = _atmRecordList  // Change ATMFields to ATMRecord

    init {
        ATMDataFetcher.fetchATMs()  // Fetch ATMs via singleton

        ATMDataFetcher.atmList.observeForever { atmList ->
            _atmRecordList.value = atmList  // Note that we no longer map it to fields
        }
    }
}

