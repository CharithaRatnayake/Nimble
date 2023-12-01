package com.nimble.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val userPreferencesRepository: UserPreferencesRepository,
    private val localAppRepository: LocalAppRepository
) : ViewModel() {

    val _authLoginResponse = MutableLiveData<Resource<LoginResponseDataModel>>()
    val authLoginResponse: LiveData<Resource<LoginResponseDataModel>> = _authLoginResponse

    private val _authResetResponse = MutableLiveData<Resource<ForgotPasswordResponseDataModel>>()
    val authResetResponse: LiveData<Resource<ForgotPasswordResponseDataModel>> = _authResetResponse

    private val _authLogoutResponse = MutableLiveData<Boolean>()
    val authLogoutResponse: LiveData<Boolean> = _authLogoutResponse

    /**
     * Initiates the login process with the provided [email] and [password].
     * Uses [viewModelScope] to launch a coroutine for handling the login operation asynchronously.
     *
     * @param email The user's email.
     * @param password The user's password.
     */
    fun login(email: String, password: String) = viewModelScope.launch {
        _authLoginResponse.value = Resource.Loading

        when (val response =
            authRepository.login(AuthTokenDataModel(email = email, password = password))) {
            is Resource.Success -> {
                _authLoginResponse.value = response
                saveTokenData(response.value.data)
                Log.d(javaClass.simpleName, "SUCCESS --> login: $response")
            }

            is Resource.Failure -> {
                _authLoginResponse.value = response
            }

            else -> {}
        }
    }

    /**
     * Initiates the registration process with the provided [name], [email], and [password].
     * Uses [viewModelScope] to launch a coroutine for handling the registration operation asynchronously.
     *
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     */
    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        Log.d(javaClass.simpleName, "register: name:$name | email:$email | password:$password")

        _authLoginResponse.value = Resource.Loading
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(
                email = email, name = name, password = password, passwordConfirmation = password
            )
        )

        when (val response = authRepository.register(registerRequestDataModel)) {
            is Resource.Success -> {
                Log.d(javaClass.simpleName, "SUCCESS --> register: $response")
                _authLoginResponse.value = response
            }

            is Resource.Failure -> {
                _authLoginResponse.value = response
            }

            else -> {}
        }

    }

    /**
     * Initiates the password reset process with the provided [email].
     * Uses [viewModelScope] to launch a coroutine for handling the password reset operation asynchronously.
     *
     * @param email The user's email for password reset.
     */
    fun reset(email: String) = viewModelScope.launch {
        Log.d(javaClass.simpleName, "reset: email:$email")

        _authResetResponse.value = Resource.Loading
        val registerRequestDataModel = RegisterRequestDataModel(
            UserDataModel(email = email)
        )

        when (val response = authRepository.forgotPassword(registerRequestDataModel)) {
            is Resource.Success -> {
                Log.d(javaClass.simpleName, "SUCCESS --> reset: $response")
                _authResetResponse.value = response
            }

            is Resource.Failure -> {
                _authLoginResponse.value = response
            }

            else -> {}
        }
    }

    /**
     * Initiates the logout process.
     * Uses [viewModelScope] to launch a coroutine for handling the logout operation asynchronously.
     */
    fun logout() = viewModelScope.launch {
        Log.d(javaClass.simpleName, "logout")
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

    /**
     * Saves token data to user preferences after a successful login.
     *
     * @param data The [TokenResponse] containing the token attributes.
     */
    private suspend fun saveTokenData(data: TokenResponse) {

        val expireIn = data.attributes.expiresIn + data.attributes.createdAt

        userPreferencesRepository.saveUserData(
            authenticated = true,
            expireIn = expireIn,
            accessToken = data.attributes.accessToken,
            refreshToken = data.attributes.refreshToken
        )
    }
}