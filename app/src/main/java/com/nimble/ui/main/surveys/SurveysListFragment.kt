package com.nimble.ui.main.surveys

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.MetaDataModel
import com.nimble.data.Resource1
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyDataModel
import com.nimble.data.UserDataModel
import com.nimble.data.local.SurveyEntity
import com.nimble.databinding.FragmentSurveysListBinding
import com.nimble.ui.main.SliderActivity
import com.nimble.ui.main.SurveysViewModel
import com.nimble.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveysListFragment :
    BaseFragment<FragmentSurveysListBinding>(R.layout.fragment_surveys_list) {

    companion object {
        const val INITIAL_PAGE = 1
        const val SURVEYS_PAGE_SIZE = 8

        fun newInstance() = SurveysListFragment()
    }

    private lateinit var viewModel: SurveysViewModel
    private lateinit var adapter: SurveyPagerAdapter
    private lateinit var metaDataModel: MetaDataModel
    private var surveyList: ArrayList<SurveyAttributeDataModel> = arrayListOf()

    override fun initUI() {

        adapter = SurveyPagerAdapter(this@SurveysListFragment)
        binding.profile.setOnClickListener {
            getCurrentActivity<SliderActivity>()?.openDrawer()
        }
        binding.btnNext.setOnClickListener {
            val currentPosition = binding.pager.currentItem
            getCurrentActivity<SliderActivity>()?.startSurveyInfoActivity(surveyList[currentPosition])

        }
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                //Scroll indicator for specific position
                val width = binding.indicator.width
                binding.indicator.scrollX =
                    ((width / surveyList.size) * position) - (binding.indicator.getChildAt(0).width * position)

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
        viewModel.userProfileResponse.observe(this) { data ->
            when (data.status) {
                Resource1.Status.LOADING -> {
                }

                Resource1.Status.SUCCESS -> {
                    data.data?.let { loadProfileData(it) }
                }

                Resource1.Status.ERROR -> {
                }
            }
        }
        viewModel.surveyListCache.observe(this) { data ->
            when (data.status) {
                Resource1.Status.LOADING -> {
                    showWaiting()
                }

                Resource1.Status.SUCCESS -> {
                    dismissWaiting()
                    loadCacheData(data.data)
                }

                Resource1.Status.ERROR -> {
                    dismissWaiting()
                    viewModel.getSurveys(INITIAL_PAGE, SURVEYS_PAGE_SIZE)
                }
            }
        }
        viewModel.getCacheSurveys()
        viewModel.getUserProfile()
    }

    private fun loadProfileData(data: UserDataModel) {
        Log.d(javaClass.simpleName, "loadProfileData: $data")
        Helper.loadCircleImageView(binding.profile.context, binding.profile, data.avatarUrl)

        getCurrentActivity<SliderActivity>()?.loadProfileData(data)
    }

    private fun loadCacheData(data: List<SurveyEntity>?) {
        Log.d(javaClass.simpleName, "loadCacheData: $data")
        val surveyAttributeDataModelList = arrayListOf<SurveyAttributeDataModel>()
        data?.forEach {
            val surveyDataModel = SurveyDataModel(
                title = it.title,
                description = it.description,
                coverImageUrl = it.coverImageUrl
            )
            val surveyAttributeDataModel =
                SurveyAttributeDataModel(id = it.id, attributes = surveyDataModel)
            surveyAttributeDataModelList.add(surveyAttributeDataModel)
        }
        loadSurveyListFromCache(surveyAttributeDataModelList)
    }

    private fun loadSurveyListFromCache(dataModel: ArrayList<SurveyAttributeDataModel>) {
        Log.d(javaClass.simpleName, "loadSurveyListFromCache: $dataModel")
        surveyList.addAll(dataModel)

        dataModel.forEach {
            val fragment = SurveyInfoFragment.newInstance(it.attributes)
            adapter.addFragment(fragment)
        }
        adapter.notifyDataSetChanged()
        binding.indicator.setViewPager(binding.pager)
    }
}