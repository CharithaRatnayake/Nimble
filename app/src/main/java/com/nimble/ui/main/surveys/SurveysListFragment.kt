package com.nimble.ui.main.surveys

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.MetaDataModel
import com.nimble.data.Resource
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyListResponseDataModel
import com.nimble.data.UserDataModel
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

                //Get next page data before the last two position
                if (adapter.itemCount == position + 2) {
                    val nextPage = metaDataModel.page + 1
                    val pageSize = metaDataModel.pageSize
                    val hasPage = nextPage <= metaDataModel.pages

                    if (hasPage)
                        viewModel.getSurveys(nextPage, pageSize)
                }
            }
        })
        binding.pager.adapter = adapter
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[SurveysViewModel::class.java]
        viewModel.userProfileResponse.observe(this) { data ->
            when (data.status) {
                Resource.Status.LOADING -> {
                    showWaiting()
                }

                Resource.Status.SUCCESS -> {
                    dismissWaiting()
                    data.data?.let { loadProfileData(it) }
                }

                Resource.Status.ERROR -> {
                    dismissWaiting()
                }
            }
        }
        viewModel.surveyListResponse.observe(this) { data ->
            when (data.status) {
                Resource.Status.LOADING -> {
                    showWaiting()
                }

                Resource.Status.SUCCESS -> {
                    dismissWaiting()
                    data.data?.let { loadSurveyListData(it) }
                    data.data?.let { metaDataModel = it.meta }
                }

                Resource.Status.ERROR -> {
                    dismissWaiting()
                }
            }
        }
        viewModel.getUserProfile()
        viewModel.getSurveys(INITIAL_PAGE, SURVEYS_PAGE_SIZE)
    }

    private fun loadProfileData(data: UserDataModel) {
        Helper.loadCircleImageView(binding.profile.context, binding.profile, data.avatarUrl)

        getCurrentActivity<SliderActivity>()?.loadProfileData(data)
    }

    private fun loadSurveyListData(dataModel: SurveyListResponseDataModel) {

        surveyList.addAll(dataModel.data)

        dataModel.data.forEach {
            val fragment = SurveyInfoFragment.newInstance(it.attributes)
            adapter.addFragment(fragment)
        }
        adapter.notifyDataSetChanged()
        binding.indicator.setViewPager(binding.pager)
    }
}