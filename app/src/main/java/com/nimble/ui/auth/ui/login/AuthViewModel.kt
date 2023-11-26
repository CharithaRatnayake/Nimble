package com.nimble.ui.auth.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.data.AppResponses
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.Resource
import com.nimble.data.TokenResponse
import com.nimble.data.remote.RemoteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: RemoteAuthRepository
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
            _authLoginResponse.value = Resource.success(response)
        }
    }
}