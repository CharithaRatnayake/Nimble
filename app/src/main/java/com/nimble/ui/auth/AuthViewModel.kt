package com.nimble.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.R
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
import com.nimble.utils.ValidatorUtil
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

    private val _isValidCredentials = MutableLiveData<Pair<Boolean, Int>>()
    val isValidCredentials: LiveData<Pair<Boolean, Int>> = _isValidCredentials

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

    /**
     * Validates the email address using a custom validator utility.
     *
     * @param email The email address to be validated.
     * @param password The password is empty check.
     */
    fun validateCredentials(email: String, password: String) {

        val validator = ValidatorUtil()

        if (!validator.isEmailValid(email)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_email)
            return
        }
        if (password.isEmpty()) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_login_password)
            return
        }
        _isValidCredentials.value = Pair(true, R.string.success)
    }

    /**
     * Validates the email address using a custom validator utility.
     *
     * @param email The email address to be validated.
     */
    fun validateCredentials(email: String) {

        val validator = ValidatorUtil()

        if (!validator.isEmailValid(email)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_email)
            return
        }
        _isValidCredentials.value = Pair(true, R.string.success)
    }

    /**
     * Validates the user credentials, including name, email, password, and re-entered password.
     *
     * @param name The user's name.
     * @param email The user's email address.
     * @param password The user's password.
     * @param rePassword The re-entered password for confirmation.
     */
    fun validateCredentials(name: String, email: String, password: String, rePassword: String) {

        val validator = ValidatorUtil()

        if (!validator.isNameValid(name)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_name)
            return
        }
        if (!validator.isEmailValid(email)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_email)
            return
        }
        if (!validator.isPasswordValid(password)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_password)
            return
        }
        if (!validator.doPasswordsMatch(password, rePassword)) {
            _isValidCredentials.value = Pair(false, R.string.error_valid_password_retype)
            return
        }
        _isValidCredentials.value = Pair(true, R.string.success)
    }
}