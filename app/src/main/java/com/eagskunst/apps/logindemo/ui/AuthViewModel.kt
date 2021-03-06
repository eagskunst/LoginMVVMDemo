package com.eagskunst.apps.logindemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eagskunst.apps.logindemo.data.*
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 7/6/2020.
 */
class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState = _loginState as LiveData<LoginViewState>

    //Check if user is authenticated
    init {
        val user = repository.currentUser()
        if (repository.currentUser() != null) {
            _loginState.postValue(Success(user))
        }
        else {
            _loginState.postValue(Initial)
        }
    }

    fun signInUser(email: String, password: String) {
        _loginState.postValue(Loading)
        viewModelScope.launch {
            val newState = repository.signInUser(
                UserCredentials(email, password)
            )
            _loginState.value = newState
        }
    }

    fun signOutUser() {
        repository.signOutUser()
        _loginState.postValue(Initial)
    }

}