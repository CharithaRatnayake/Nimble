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

    /**
     * Initiates the retrieval of the user profile.
     * Uses [viewModelScope] to launch a coroutine for handling the user profile retrieval operation asynchronously.
     */

    fun getUserProfile() = viewModelScope.launch {
        Log.d(javaClass.simpleName, "getUserProfile")

        when (val response =
            appRepository.getUserProfile()) {
            is Resource.Success -> {
                Log.d(javaClass.simpleName, "SUCCESS --> getUserProfile: $response")
                _userProfileResponse.value = response
            }

            is Resource.Failure -> {
                _userProfileResponse.value = response
            }

            else -> {}
        }
    }

    /**
     * Initiates the process to refresh surveys by deleting all existing surveys locally
     * and then fetching new surveys from the remote source.
     *
     * @param initialPage The initial page number for survey retrieval.
     * @param surveysPageSize The number of surveys to retrieve per page.
     */
    fun getRefreshSurveys(initialPage: Int, surveysPageSize: Int) = viewModelScope.launch {
        localAppRepository.deleteAllSurveys()

        getSurveys(initialPage, surveysPageSize)
    }

    /**
     * Initiates the retrieval of surveys from the remote source for a specified page and page size.
     *
     * @param page The page number for survey retrieval.
     * @param pageSize The number of surveys to retrieve per page.
     */
    fun getSurveys(page: Int, pageSize: Int) = viewModelScope.launch {
        Log.d(javaClass.simpleName, "getSurveys + [Page: $page] [Page Size: $pageSize]")

        when (val response =
            appRepository.getSurveys(page, pageSize)) {
            is Resource.Success -> {
                Log.d(javaClass.simpleName, "SUCCESS --> getSurveys: $response")
                setSurveysToCache(response.value.data)
            }

            is Resource.Failure -> {
                _surveyListResponse.value = response
            }

            else -> {}
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