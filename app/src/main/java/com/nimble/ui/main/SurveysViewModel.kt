package com.nimble.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyListResponseDataModel
import com.nimble.data.UserResponseDataModel
import com.nimble.data.http.Resource
import com.nimble.data.local.SurveyEntity
import com.nimble.di.repository.LocalAppRepository
import com.nimble.di.repository.RemoteAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveysViewModel @Inject constructor(
    private val appRepository: RemoteAppRepository,
    private val localAppRepository: LocalAppRepository
) : ViewModel() {

    private val _userProfileResponse = MutableLiveData<Resource<UserResponseDataModel>>()

    val userProfileResponse: LiveData<Resource<UserResponseDataModel>> = _userProfileResponse

    private val _surveyListResponse = MutableLiveData<Resource<SurveyListResponseDataModel>>()

    val surveyListResponse: LiveData<Resource<SurveyListResponseDataModel>> = _surveyListResponse

    private val _surveyListCache = MutableLiveData<List<SurveyEntity>>()

    val surveyListCache: LiveData<List<SurveyEntity>> = _surveyListCache

    fun getUserProfile() {
        Log.d(javaClass.simpleName, "getUserProfile")

        viewModelScope.launch {
            val response = appRepository.getUserProfile()

            response.let { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.d(javaClass.simpleName, "SUCCESS --> getUserProfile: $data")
                        _userProfileResponse.value = data
                    }

                    is Resource.Failure -> {
                        _userProfileResponse.value = data
                    }

                    else -> {}
                }
            }
        }
    }

    fun getSurveys(page: Int, pageSize: Int) {
        Log.d(javaClass.simpleName, "getSurveys + [Page: $page] [Page Size: $pageSize]")

        viewModelScope.launch {
            val response = appRepository.getSurveys(page, pageSize)

            response.let { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.d(javaClass.simpleName, "SUCCESS --> getSurveys: $data")
                        setSurveysToCache(data.value.data)
                    }

                    is Resource.Failure -> {
                        _surveyListResponse.value = data
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setSurveysToCache(data: ArrayList<SurveyAttributeDataModel>) {
        Log.d(javaClass.simpleName, "setSurveysToCache")

        val surveyEntityList = arrayListOf<SurveyEntity>()
        data.forEach {

            val surveyEntity = SurveyEntity(
                it.id, it.attributes.title, it.attributes.description, it.attributes.coverImageUrl
            )
            surveyEntityList.add(surveyEntity)
        }

        viewModelScope.launch {
            localAppRepository.insertSurveys(surveyEntityList)

            _surveyListCache.value = surveyEntityList
        }
    }

    fun getCacheSurveys() {
        Log.d(javaClass.simpleName, "getCacheSurveys")

        viewModelScope.launch {
            val surveysList = localAppRepository.getAllSurveys()

            _surveyListCache.value = surveysList
        }
    }

}