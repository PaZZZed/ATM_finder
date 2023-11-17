package com.example.mobg5_53204.fragments.atm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobg5_53204.database.MyDatabase
import com.example.mobg5_53204.database.api.ATMDataFetcher
import com.example.mobg5_53204.database.api.ATMFieldsObject
import com.example.mobg5_53204.database.favoris.FavorisRepository
import com.example.mobg5_53204.model.favoris.Favoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ATMDetailViewModel(application: Application) : AndroidViewModel(application) {


    val isFavori = MutableLiveData<Boolean>()


    val agence: MutableLiveData<String?> = MutableLiveData()
    val adresseFr: MutableLiveData<String?> = MutableLiveData()
    val adresseNl: MutableLiveData<String?> = MutableLiveData()

    private val favorisDao = MyDatabase.getInstance(application).favorisDAO
    private val favorisRepository = FavorisRepository(favorisDao)

    fun addFavoris(atmId: String, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoris = Favoris(atmId = atmId, userEmail = userEmail)
            favorisRepository.addFavoris(favoris)
        }
    }
    fun checkIfFavori(atmId: String, userEmail: String) {
        val favoriCount = favorisRepository.isFavoriForUser(atmId, userEmail)
        favoriCount.observeForever { count ->
            isFavori.value = (count > 0)
        }
    }
    fun fetchATMDetailsById(atmId: String) {
        Log.d("RECORD", "Fetching ATM details for ID: $atmId")
        ATMDataFetcher.fetchATMById(atmId) { atmRecord ->
            if (atmRecord != null) {
                Log.d("RECORD", "Received ATM details: $atmRecord")

                updateATMDetails(
                    atmRecord.agence,
                    atmRecord.adresse_fr,
                    atmRecord.adresse_nl
                )
            } else {
                Log.d("RECORD", "Failed to fetch ATM details for ID: $atmId")
            }
        }
    }

    private fun updateATMDetails(agence: String?, adresseFr: String?, adresseNl: String?) {
        this.agence.postValue(agence)
        this.adresseFr.postValue(adresseFr)
        this.adresseNl.postValue(adresseNl)
    }
}
