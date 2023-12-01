package com.nimble.ui.main.survey

import android.os.Bundle
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyDataModel
import com.nimble.databinding.FragmentSurveyBinding
import com.nimble.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyFragment :
    BaseFragment<FragmentSurveyBinding>(R.layout.fragment_survey) {

    companion object {
        const val KEY_DATA = "com.nimble.ui.main.KEY_DATA"
        const val HIGH_IMAGE_QUALITY = "l"
        fun newInstance(data: SurveyAttributeDataModel) =
            SurveyFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_DATA, data)
                }
            }
    }

    private var dataModel: SurveyAttributeDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataModel = it.getSerializable(KEY_DATA) as? SurveyAttributeDataModel
        }
    }

    override fun initUI() {
        binding.btnStartSurvey.setOnClickListener {
            dataModel?.let {
                getCurrentActivity<SurveyActivity>()?.startQuestionsFragment(it.id)
            }
        }
        dataModel?.let { loadSurveyData(it.attributes) }
    }

    override fun initViewModel() {

    }

    fun loadSurveyData(data: SurveyDataModel) {
        binding.title.text = data.title
        binding.description.text = data.description

        Helper.loadImageView(binding.preview.context, binding.preview, data.coverImageUrl.plus(HIGH_IMAGE_QUALITY))
    }
}