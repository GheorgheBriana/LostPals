package com.example.lostpals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lostpals.data.database.AppDatabase
import com.example.lostpals.data.dto.LoginRequest
import com.example.lostpals.data.entity.User
import com.example.lostpals.repository.UserRepository
import com.example.lostpals.util.Resource
import com.example.lostpals.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    val registrationStatus = MutableLiveData<Resource<Long>>()
    val loginStatus = MutableLiveData<Resource<User>>()
    val sessionManager = SessionManager(application)

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = repository.registerUser(username, email, password)
                registrationStatus.postValue(Resource.Success(userId))
            } catch (e: Exception) {
                registrationStatus.postValue(Resource.Error(e.message ?: "Registration failed", null))
            }
        }
    }

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = repository.loginUser(request.username, request.password)
                sessionManager.saveUserId(user.id)
                sessionManager.setLoggedIn(true)
                loginStatus.postValue(Resource.Success(user))
            } catch (e: Exception) {
                loginStatus.postValue(Resource.Error(e.message ?: "Login failed", null))
            }
        }
    }

    fun logout(){
        sessionManager.logout()
    }
}