package com.example.drug_app2021.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.drug_app2021.data.network.Resource
import com.example.drug_app2021.data.repository.AuthRepository
import com.example.drug_app2021.data.response.LoginResponse
import com.example.drug_app2021.data.response.StatusResponse
import com.example.drug_app2021.data.db.entity.User
import com.example.drug_app2021.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository){

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _registerResponse : MutableLiveData<Resource<StatusResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<StatusResponse>>
        get() = _registerResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
        Log.d("_LoginResponse",repository.login(email, password).toString())
    }

    fun register(
        name: String,
        email: String,
        password: String,
        cellphone: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(name, email, password, cellphone)
        Log.d("_registerResponse",repository.register(name, email, password, cellphone).toString())
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

    suspend fun saveUser(user: User) {
        repository.saveUser(user)
    }
}