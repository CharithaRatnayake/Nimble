package com.nimble.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.data.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun getCacheData() {

        viewModelScope.launch {
            userPreferencesRepository.email.collect { email ->
                _isLoggedIn.value = !email.isNullOrEmpty()
            }
        }
    }
}