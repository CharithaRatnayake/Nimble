package com.nimble.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.data.Resource1
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.UserDataModel
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

    private val _userProfileResponse = MutableLiveData<Resource1<UserDataModel>>()

    val userProfileResponse: LiveData<Resource1<UserDataModel>> = _userProfileResponse

    private val _surveyListCache = MutableLiveData<Resource1<List<SurveyEntity>>>()

    val surveyListCache: LiveData<Resource1<List<SurveyEntity>>> = _surveyListCache

    fun getUserProfile() {
        Log.d(javaClass.simpleName, "getUserProfile")

        viewModelScope.launch {
            val response = appRepository.getUserProfile()

            if (response.isSuccessful) {
                val body = response.body()
                _userProfileResponse.value =
                    Resource1(
                        Resource1.Status.SUCCESS,
                        body?.userDataModel?.attributes,
                        response.message()
                    )
            }
        }
    }

    fun getSurveys(page: Int, pageSize: Int) {
        Log.d(javaClass.simpleName, "getSurveys + [Page: $page] [Page Size: $pageSize]")

        viewModelScope.launch {
            val response = appRepository.getSurveys(page, pageSize)

            if (response.isSuccessful) {
                val body = response.body()

                body?.let { setSurveysToCache(it.data) }
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

            _surveyListCache.value =
                Resource1(Resource1.Status.SUCCESS, surveyEntityList, "")
        }
    }

    fun getCacheSurveys() {
        Log.d(javaClass.simpleName, "getCacheSurveys")

        _surveyListCache.value = Resource1.loading()
        viewModelScope.launch {
            val surveysList = localAppRepository.getAllSurveys()

            if (surveysList.isEmpty()) {
                _surveyListCache.value =
                    Resource1(Resource1.Status.ERROR, surveysList, "")
            } else {
                _surveyListCache.value =
                    Resource1(Resource1.Status.SUCCESS, surveysList, "")
            }
        }
    }

}