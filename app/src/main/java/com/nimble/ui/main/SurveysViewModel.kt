package com.nimble.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.data.AuthTokenDataModel
import com.nimble.data.LoginResponseDataModel
import com.nimble.data.Resource
import com.nimble.data.SurveyListResponseDataModel
import com.nimble.data.UserDataModel
import com.nimble.data.UserResponseDataModel
import com.nimble.data.local.UserPreferencesRepository
import com.nimble.data.remote.RemoteAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveysViewModel @Inject constructor(
    private val appRepository: RemoteAppRepository,
    private val gson: Gson,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _userProfileResponse = MutableLiveData<Resource<UserDataModel>>()

    val userProfileResponse: LiveData<Resource<UserDataModel>> = _userProfileResponse

    private val _surveyListResponse = MutableLiveData<Resource<SurveyListResponseDataModel>>()

    val surveyListResponse: LiveData<Resource<SurveyListResponseDataModel>> = _surveyListResponse

    fun getUserProfile() {
        Log.d(javaClass.simpleName, "getUserProfile")

        _userProfileResponse.value = Resource.loading()
        viewModelScope.launch {
            val response = appRepository.getUserProfile()

            if (response.isSuccessful) {
                val body = response.body()
                _userProfileResponse.value =
                    Resource(Resource.Status.SUCCESS, body?.userDataModel?.attributes, response.message())
            }
        }
    }

    fun getSurveys(page: Int, pageSize: Int) {
        Log.d(javaClass.simpleName, "getSurveys + [Page: $page] [Page Size: $pageSize]")

        _userProfileResponse.value = Resource.loading()
        viewModelScope.launch {
            val response = appRepository.getSurveys(page, pageSize)

            if (response.isSuccessful) {
                val body = response.body()
                _surveyListResponse.value =
                    Resource(Resource.Status.SUCCESS, body, response.message())
            }
        }
    }

}