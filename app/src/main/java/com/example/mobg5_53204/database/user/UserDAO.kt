package com.example.mobg5_53204.database.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobg5_53204.model.user.User

@Dao
interface UserDAO {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from users_table")
    fun getAll(): LiveData<User>

    @Query("SELECT * FROM users_table WHERE email = :email")
    fun getAt(email: String): User

    @Query("SELECT email from users_table")
    fun getAllEmails(): LiveData<List<String>>
}