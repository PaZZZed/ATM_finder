package com.example.mobg5_53204.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//each class that has entity, room creates a table
@Entity(tableName = "users_table")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "last_time_connected")
    var time: String,
) {

}