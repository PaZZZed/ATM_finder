package com.example.mobg5_53204.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobg5_53204.database.favoris.FavorisDAO
import com.example.mobg5_53204.model.user.User
import com.example.mobg5_53204.database.user.UserDAO
import com.example.mobg5_53204.model.favoris.Favoris

@Database(
    entities = [User::class, Favoris::class],
    version = 3,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO
    abstract val favorisDAO: FavorisDAO

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "53204_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
