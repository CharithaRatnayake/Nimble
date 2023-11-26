package com.nimble.ui.auth.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.Resource
import com.nimble.data.TokenResponse
import com.nimble.data.UserDataModel
import com.nimble.data.remote.RemoteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: RemoteAuthRepository,
    private val gson: Gson
) : ViewModel() {

    private val _authLoginResponse = MutableLiveData<Resource<AppResponses<TokenResponse>>>()

    val authLoginResponse: LiveData<Resource<AppResponses<TokenResponse>>> = _authLoginResponse

    fun login(email: String, password: String) {
        Log.d(javaClass.simpleName, "login: email:$email and password:$password")

        _authLoginResponse.value = Resource.loading()
        val authTokenDataModel = AuthTokenDataModel(
            email = email, password = password
        )
        viewModelScope.launch {
            val response = authRepository.login(authTokenDataModel)

            if (response.isSuccessful){
                val body = response.body()
                _authLoginResponse.value = Resource(Resource.Status.SUCCESS, body, response.message())
            } else{
                val errorBody = response.errorBody()?.string()
                val error: AppResponses<TokenResponse> = gson.fromJson(errorBody, object : TypeToken<AppResponses<TokenResponse>>() {}.type)
                _authLoginResponse.value = Resource(Resource.Status.ERROR, error, response.message())
            }
        }
    }

    fun register(name: String, email: String, password: String, rePassword: String) {
        Log.d(javaClass.simpleName, "register: name:$name | email:$email | password:$password")

        _authLoginResponse.value = Resource.loading()
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(
                email, name, password, rePassword
            )
        )
        return
        //API does not send a success object
        viewModelScope.launch {
            val response = authRepository.register(registerRequestDataModel)

            if (response.isSuccessful){
                _authLoginResponse.value = Resource(Resource.Status.SUCCESS, null, response.message())
            } else{
                val errorBody = response.errorBody()?.string()
                val error: AppResponses<TokenResponse> = gson.fromJson(errorBody, object : TypeToken<AppResponses<TokenResponse>>() {}.type)
                _authLoginResponse.value = Resource(Resource.Status.ERROR, error, response.message())
            }
        }
    }
}