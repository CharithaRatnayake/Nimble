package com.nimble.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.ForgotPasswordResponseDataModel
import com.nimble.data.LoginResponseDataModel
import com.nimble.data.RegisterRequestDataModel
import com.nimble.data.TokenResponse
import com.nimble.data.UserDataModel
import com.nimble.data.http.Resource
import com.nimble.di.repository.LocalAppRepository
import com.nimble.di.repository.RemoteAuthRepository
import com.nimble.di.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: RemoteAuthRepository,
    private val gson: Gson,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val localAppRepository: LocalAppRepository
) : ViewModel() {

    private val _authLoginResponse = MutableLiveData<Resource<LoginResponseDataModel>>()

    val authLoginResponse: LiveData<Resource<LoginResponseDataModel>> = _authLoginResponse

    private val _authResetResponse = MutableLiveData<Resource<ForgotPasswordResponseDataModel>>()

    val authResetResponse: LiveData<Resource<ForgotPasswordResponseDataModel>> = _authResetResponse

    private val _authLogoutResponse = MutableLiveData<Boolean>()

    val authLogoutResponse: LiveData<Boolean> = _authLogoutResponse

    fun login(email: String, password: String) {
        Log.d(javaClass.simpleName, "login: email:$email and password:$password")

        _authLoginResponse.value = Resource.Loading
        val authTokenDataModel = AuthTokenDataModel(
            email = email, password = password
        )
        viewModelScope.launch {
            val response = authRepository.login(authTokenDataModel)

            response.let { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.d(javaClass.simpleName, "SUCCESS --> login: $data")
                        _authLoginResponse.value = data

                        saveTokenData(data.value.data)
                    }

                    is Resource.Failure -> {
                        _authLoginResponse.value = data
                    }

                    else -> {}
                }
            }
        }
    }

    private suspend fun saveTokenData(data: TokenResponse) {
        val expireIn = data.attributes.expiresIn + data.attributes.createdAt
        userPreferencesRepository.saveUserData(
            true, expireIn, data.attributes.accessToken, data.attributes.refreshToken
        )
    }

    fun register(name: String, email: String, password: String) {
        Log.d(javaClass.simpleName, "register: name:$name | email:$email | password:$password")

        _authLoginResponse.value = Resource.Loading
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(
                email = email, name = name, password = password, passwordConfirmation = password
            )
        )

        viewModelScope.launch {
            val response = authRepository.register(registerRequestDataModel)

            response.let { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.d(javaClass.simpleName, "SUCCESS --> register: $data")
                        _authLoginResponse.value = data
                    }

                    is Resource.Failure -> {
                        _authLoginResponse.value = data
                    }

                    else -> {}
                }
            }
        }

    }

    fun reset(email: String) {
        Log.d(javaClass.simpleName, "reset: email:$email")

        _authResetResponse.value = Resource.Loading
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(email = email)
        )
        viewModelScope.launch {
            val response = authRepository.forgotPassword(registerRequestDataModel)

            response.let { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.d(javaClass.simpleName, "SUCCESS --> register: $data")
                        _authResetResponse.value = data
                    }

                    is Resource.Failure -> {
                        _authLoginResponse.value = data
                    }

                    else -> {}
                }
            }
        }
    }

    fun logout() {
        Log.d(javaClass.simpleName, "logout")
        viewModelScope.launch {
            val token = userPreferencesRepository.accessToken.firstOrNull()

            token?.let {
                val response = authRepository.logout(
                    AuthTokenDataModel(
                        token = token
                    )
                )

                response.let { data ->
                    when (data) {
                        is Resource.Success -> {
                            Log.d(
                                javaClass.simpleName,
                                "SUCCESS --> User successfully logged out: $data"
                            )
                            userPreferencesRepository.clearDataStore()
                            localAppRepository.deleteAllSurveys()
                            _authLogoutResponse.value = true
                        }

                        is Resource.Failure -> {
                            _authLogoutResponse.value = false
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}