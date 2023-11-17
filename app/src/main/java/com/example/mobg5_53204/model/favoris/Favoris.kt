package com.example.mobg5_53204.model.favoris

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoris_table")
data class Favoris(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "atm_id")
    val atmId: String,

    @ColumnInfo(name = "user_email")
    val userEmail: String
)
