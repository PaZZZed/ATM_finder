package com.example.mobg5_53204.database.user

import androidx.lifecycle.LiveData
import com.example.mobg5_53204.model.user.User

class UserRepository(private val userDao: UserDAO) {

    fun addUser(user: User) {
        userDao.insert(user)
    }

    fun updateUser(user: User) {
        userDao.update(user)
    }

    fun deleteUser(user: User) {
        userDao.delete(user)
    }

    fun getUsers(): LiveData<User> {
        return userDao.getAll()
    }

    fun getUserAt(email: String): User {
        return userDao.getAt(email)
    }

    fun getAllEmails(): LiveData<List<String>> {
        return userDao.getAllEmails()
    }


}