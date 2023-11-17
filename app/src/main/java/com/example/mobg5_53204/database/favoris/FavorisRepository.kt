package com.example.mobg5_53204.database.favoris

import androidx.lifecycle.LiveData
import com.example.mobg5_53204.model.favoris.Favoris

class FavorisRepository(private val favorisDAO: FavorisDAO) {

    fun addFavoris(favoris: Favoris) {
        favorisDAO.insert(favoris)
    }

    fun removeFavoris(favoris: Favoris) {
        favorisDAO.delete(favoris)
    }

    fun getFavorisForUser(email: String): LiveData<List<Favoris>> {
        return favorisDAO.getFavorisForUser(email)
    }

    fun getAllFavoris(): LiveData<List<Favoris>> {
        return favorisDAO.getAllFavoris()
    }


    fun deleteForUser(email: String) {
        return favorisDAO.deleteForUser(email)
    }

    fun clear() {
        favorisDAO.clear()
    }

    fun isFavoriForUser(atmId: String, email: String): LiveData<Int> {
        return favorisDAO.isFavoriForUser(atmId, email)
    }


}
