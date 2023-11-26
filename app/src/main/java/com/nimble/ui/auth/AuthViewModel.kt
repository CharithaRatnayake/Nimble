package com.nimble.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.ForgotPasswordResponseDataModel
import com.nimble.data.LoginResponseDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.Resource
import com.nimble.data.UserDataModel
import com.nimble.data.local.UserPreferencesRepository
import com.nimble.data.remote.RemoteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: RemoteAuthRepository,
    private val gson: Gson,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _authLoginResponse = MutableLiveData<Resource<LoginResponseDataModel>>()

    val authLoginResponse: LiveData<Resource<LoginResponseDataModel>> = _authLoginResponse

    private val _authResetResponse = MutableLiveData<Resource<ForgotPasswordResponseDataModel>>()

    val authResetResponse: LiveData<Resource<ForgotPasswordResponseDataModel>> = _authResetResponse

    fun login(email: String, password: String) {
        Log.d(javaClass.simpleName, "login: email:$email and password:$password")

        _authLoginResponse.value = Resource.loading()
        val authTokenDataModel = AuthTokenDataModel(
            email = email, password = password
        )
        viewModelScope.launch {
            val response = authRepository.login(authTokenDataModel)

            if (response.isSuccessful) {
                val body = response.body()
                _authLoginResponse.value =
                    Resource(Resource.Status.SUCCESS, body, response.message())

                body?.let {
                    userPreferencesRepository.saveUserData("", email, it.data.attributes.accessToken, it.data.attributes.refreshToken)
                }

            } else {
                val errorBody = response.errorBody()?.string()
                val error: LoginResponseDataModel =
                    gson.fromJson(errorBody, object : TypeToken<LoginResponseDataModel>() {}.type)
                _authLoginResponse.value =
                    Resource(Resource.Status.ERROR, error, response.message())
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
        //TODO API does not send a success object

    }

    fun reset(email: String) {
        Log.d(javaClass.simpleName, "reset: email:$email")

        _authResetResponse.value = Resource.loading()
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(email = email)
        )
        viewModelScope.launch {
            val response = authRepository.forgotPassword(registerRequestDataModel)

            if (response.isSuccessful) {
                val body = response.body()
                _authResetResponse.value =
                    Resource(Resource.Status.SUCCESS, body, response.message())
            }
        }
    }
}