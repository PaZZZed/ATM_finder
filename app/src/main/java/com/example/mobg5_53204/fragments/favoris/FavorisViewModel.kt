package com.example.mobg5_53204.fragments.favoris

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobg5_53204.database.MyDatabase
import com.example.mobg5_53204.database.api.ATMDataFetcher
import com.example.mobg5_53204.database.api.ATMFields
import com.example.mobg5_53204.database.api.ATMRecord
import com.example.mobg5_53204.database.favoris.FavorisRepository
import com.example.mobg5_53204.model.favoris.Favoris
import com.example.mobg5_53204.model.user.UserSession
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class FavorisViewModel(application: Application) : AndroidViewModel(application) {

    //  private val favorisDao = MyDatabase.getInstance(application).favorisDAO
    private val favorisRepository: FavorisRepository

    val userFavoris: LiveData<List<Favoris>>

    val favoriteATMRecords: MutableLiveData<List<ATMRecord>> = MutableLiveData()

    init {
        //start DB
        val favorisDao = MyDatabase.getInstance(application).favorisDAO
        // Log.d("FAVORIS DAO", "Fetching ATM details for ID: ${favorisDao.getAllFavoris().value}")

        favorisRepository = FavorisRepository(favorisDao) //
        // Log.d("FAVORIS", "Fetching ATM details for ID: ${favorisRepository.getAllFavoris().value}")
        // Obtenez l'e-mail de l'utilisateur à partir du singleton
        val userEmail = UserSession.userEmail

        userFavoris = favorisRepository.getFavorisForUser(userEmail)



        Log.i("GameViewModel", "GameViewModel created!")
    }

    fun removeFavoris() {
        viewModelScope.launch(Dispatchers.IO) {
            val userEmail = UserSession.userEmail
            favorisRepository.deleteForUser(userEmail)
            favoriteATMRecords.postValue(emptyList())
        }
    }


    fun getFavoriteATMRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorisList = userFavoris.value ?: return@launch

            val atmRecordList = mutableListOf<ATMRecord>()

            val jobCount = AtomicInteger(favorisList.size)

            favorisList.forEach { favoris ->
                launch {
                    ATMDataFetcher.fetchATMById(favoris.atmId) { atmFieldsObject ->
                        if (atmFieldsObject != null) {
                            val atmFields = ATMFields(
                                coord = listOf(
                                    atmFieldsObject.coord.lat,
                                    atmFieldsObject.coord.lon
                                ),
                                atmFieldsObject.adresse_fr,
                                atmFieldsObject.adresse_nl,
                                atmFieldsObject.agence
                            )
                            atmRecordList.add(ATMRecord(favoris.atmId, atmFields))
                        }
                        if (jobCount.decrementAndGet() == 0) {
                            favoriteATMRecords.postValue(atmRecordList)
                        }
                    }
                }
            }
        }
    }

}
