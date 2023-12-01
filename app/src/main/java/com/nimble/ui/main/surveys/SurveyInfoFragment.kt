package com.nimble.ui.main.surveys

import android.os.Bundle
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.SurveyDataModel
import com.nimble.databinding.FragmentSurveyInfoBinding
import com.nimble.utils.loadImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyInfoFragment :
    BaseFragment<FragmentSurveyInfoBinding>(R.layout.fragment_survey_info) {

    companion object {
        const val KEY_DATA = "com.nimble.ui.main.KEY_DATA"
        const val HIGH_IMAGE_QUALITY = "l"
        fun newInstance(data: SurveyDataModel) =
            SurveyInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_DATA, data)
                }
            }
    }

    private var dataModel: SurveyDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataModel = it.getSerializable(KEY_DATA) as? SurveyDataModel
        }
    }

    override fun initUI() {
        dataModel?.let { loadSurveyData(it) }
    }

    override fun initViewModel() {

    }

    private fun loadSurveyData(data: SurveyDataModel) {
        binding.title.text = data.title
        binding.description.text = data.description

        binding.preview.loadImageView(
            data.coverImageUrl.plus(HIGH_IMAGE_QUALITY)
        )
    }
}