package com.nimble.ui.main.surveys

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.MetaDataModel
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyDataModel
import com.nimble.data.UserDataModel
import com.nimble.data.http.Resource
import com.nimble.data.local.SurveyEntity
import com.nimble.databinding.FragmentSurveysListBinding
import com.nimble.ui.main.SliderActivity
import com.nimble.ui.main.SurveysViewModel
import com.nimble.utils.getDateAndTimeForFormat
import com.nimble.utils.loadCircleImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SurveysListFragment :
    BaseFragment<FragmentSurveysListBinding>(R.layout.fragment_surveys_list) {

    companion object {
        const val INITIAL_PAGE = 1
        const val SURVEYS_PAGE_SIZE = 8
        const val DATE_FORMAT = "EEEE, MMMM dd"

        fun newInstance() = SurveysListFragment()
    }

    private lateinit var viewModel: SurveysViewModel
    private lateinit var adapter: SurveyPagerAdapter
    private lateinit var metaDataModel: MetaDataModel
    private var surveyList: ArrayList<SurveyAttributeDataModel> = arrayListOf()

    override fun initUI() {

        binding.progressView.startAnimation()
        adapter = SurveyPagerAdapter(this@SurveysListFragment)
        binding.date.text = Date().getDateAndTimeForFormat(DATE_FORMAT)
        binding.profile.setOnClickListener {
            getCurrentActivity<SliderActivity>()?.openDrawer()
        }
        binding.btnNext.setOnClickListener {
            val currentPosition = binding.pager.currentItem
            getCurrentActivity<SliderActivity>()?.startSurveyInfoActivity(surveyList[currentPosition])

        }
        binding.progressView.onClickRetryListener = {
            //if network failure call new data from the api
            viewModel.getSurveys(INITIAL_PAGE, SURVEYS_PAGE_SIZE)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            //Swipe to refresh function call new data from the api
            binding.progressView.startAnimation()
            viewModel.getRefreshSurveys(INITIAL_PAGE, SURVEYS_PAGE_SIZE)

            surveyList.clear()
            adapter = SurveyPagerAdapter(this@SurveysListFragment)
            binding.pager.adapter = adapter
        }
        //view pager adaptor OnPageChangeCallback
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.circleIndicatorView.selectCircles(position)

                // Get next page data before the last two positions
                if (adapter.itemCount == position + 2) {
                    if (::metaDataModel.isInitialized) {
                        // If `metaDataModel` is initialized, use its properties to determine the next page
                        val nextPage = metaDataModel.page + 1
                        val pageSize = metaDataModel.pageSize
                        val hasPage = nextPage <= metaDataModel.pages

                        if (hasPage) viewModel.getSurveys(nextPage, pageSize)
                    } else {
                        // If `metaDataModel` is not initialized, use the size of the current list to determine the next page
                        val size = surveyList.size
                        val nextPage = size / SURVEYS_PAGE_SIZE + 1
                        val hasPage = size / SURVEYS_PAGE_SIZE * SURVEYS_PAGE_SIZE == size

                        if (hasPage) viewModel.getSurveys(nextPage, SURVEYS_PAGE_SIZE)
                    }
                }
            }
        })
        binding.pager.adapter = adapter
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[SurveysViewModel::class.java]
        // Observe the userProfileResponse LiveData in the ViewModel
        viewModel.userProfileResponse.observe(this) { data ->
            when (data) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    loadProfileData(data.value.userDataModel.attributes)
                }

                is Resource.Failure -> {
                    showError(data)
                }
            }
        }
        // Observe the surveyListCache LiveData in the ViewModel
        viewModel.surveyListCache.observe(this) { data ->

            if (data.isEmpty()) {
                viewModel.getSurveys(INITIAL_PAGE, SURVEYS_PAGE_SIZE)
            } else {
                loadCacheData(data)
            }
        }
        // Observe the surveyListResponse LiveData in the ViewModel
        viewModel.surveyListResponse.observe(this) { data ->
            when (data) {
                is Resource.Loading -> {
                    showWaiting()
                }

                is Resource.Success -> {
                    dismissWaiting()
                }

                is Resource.Failure -> {
                    dismissWaiting()
                    binding.progressView.setError(data)
                }
            }
        }

        viewModel.getCacheSurveys()
        viewModel.getUserProfile()
    }

    /**
     * Loads profile data into the UI based on the provided UserDataModel.
     *
     * @param data The UserDataModel containing profile information.
     */
    private fun loadProfileData(data: UserDataModel) {
        Log.d(javaClass.simpleName, "loadProfileData: $data")
        binding.profile.loadCircleImage(data.avatarUrl)

        getCurrentActivity<SliderActivity>()?.loadProfileData(data)
    }

    /**
     * Loads survey data from cache and transforms it into a list of SurveyAttributeDataModel.
     *
     * @param data The list of SurveyEntity retrieved from the cache.
     */
    private fun loadCacheData(data: List<SurveyEntity>?) {
        Log.d(javaClass.simpleName, "loadCacheData: $data")
        val surveyAttributeDataModelList = arrayListOf<SurveyAttributeDataModel>()
        data?.forEach {
            val surveyDataModel = SurveyDataModel(
                title = it.title, description = it.description, coverImageUrl = it.coverImageUrl
            )
            val surveyAttributeDataModel =
                SurveyAttributeDataModel(id = it.id, attributes = surveyDataModel)
            surveyAttributeDataModelList.add(surveyAttributeDataModel)
        }
        loadSurveyListFromCache(surveyAttributeDataModelList)
    }

    /**
     * Loads survey list from the cache data and updates the UI components accordingly.
     *
     * @param dataModel The list of SurveyAttributeDataModel retrieved from the cache.
     */
    private fun loadSurveyListFromCache(dataModel: ArrayList<SurveyAttributeDataModel>) {
        Log.d(javaClass.simpleName, "loadSurveyListFromCache: $dataModel")
        surveyList.addAll(dataModel)
        binding.circleIndicatorView.addCircles(surveyList.size)

        dataModel.forEach {
            val fragment = SurveyInfoFragment.newInstance(it.attributes)
            adapter.addFragment(fragment)
            adapter.notifyDataSetChanged()
            binding.progressView.stopAnimation()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}