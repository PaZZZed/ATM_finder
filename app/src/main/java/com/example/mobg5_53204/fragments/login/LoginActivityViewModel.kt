package com.example.mobg5_53204.fragments.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
//database
import com.example.mobg5_53204.model.user.User
import com.example.mobg5_53204.database.MyDatabase
import com.example.mobg5_53204.database.user.UserRepository
//coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//to use calendar date
import java.util.*
import java.text.SimpleDateFormat


//viewModel survive changes donc tt data y est
class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    //pour les toasts
    private val _status = MutableLiveData<Boolean>()
    val status: MutableLiveData<Boolean>
        get() = _status

    private var repository: UserRepository
    val data: LiveData<List<String>>

    private val returnedVal = MutableLiveData<List<String>>()


    init {
        //start DB
        val userDao = MyDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao) //

        data = repository.getAllEmails() //get all emails

        Log.i("GameViewModel", "GameViewModel created!")
    }

    //indice emails
    fun populateEmail() {
        viewModelScope.launch {
            returnedVal.value = repository.getAllEmails().value
        }
    }

    private fun getByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getUserAt(email).email
            } catch (e: Exception) {
                repository.addUser(User(email = email, time = getCurrentTime()))
            }
            repository.updateUser(User(email = email, time = getCurrentTime()))
        }
    }

    /**
     * Email check
     */
    fun emailCheck(str: String) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (str.isEmpty()) {
            _status.value = false
        } else {
            if (str.trim().matches(emailPattern.toRegex())) {
                getByEmail(str)

                viewModelScope.launch(Dispatchers.IO) { // coroutine on Main
                    // println(repo.get("hello@hotmail.be").email)
                }
                _status.value = true
            } else {
                _status.value = false
            }
        }
    }

    private fun getCurrentTime(): String {
        val current = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(current)
    }

    fun clear(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
}