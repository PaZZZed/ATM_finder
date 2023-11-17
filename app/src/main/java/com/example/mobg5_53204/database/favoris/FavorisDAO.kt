package com.example.mobg5_53204.database.favoris

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mobg5_53204.model.favoris.Favoris

@Dao
interface FavorisDAO {

    @Insert
    fun insert(favoris: Favoris)

    @Delete
    fun delete(favoris: Favoris)

    @Query("SELECT * from favoris_table WHERE user_email = :email")
    fun getFavorisForUser(email: String): LiveData<List<Favoris>>

    @Query("SELECT * from favoris_table")
    fun getAllFavoris(): LiveData<List<Favoris>>

    @Query("DELETE FROM favoris_table")
    fun clear()

    @Query("DELETE FROM favoris_table WHERE  user_email = :email")
    fun deleteForUser(email: String)

    @Query("SELECT COUNT(*) FROM favoris_table WHERE atm_id = :atmId AND user_email = :email")
    fun isFavoriForUser(atmId: String, email: String): LiveData<Int>

}
