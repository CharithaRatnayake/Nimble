package com.nimble.ui.main.questions

import android.os.Bundle
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.SurveyDataModel
import com.nimble.databinding.FragmentQuestionsBinding
import com.nimble.databinding.FragmentSurveyBinding
import com.nimble.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment :
    BaseFragment<FragmentQuestionsBinding>(R.layout.fragment_questions) {

    companion object {
        const val KEY_DATA = "com.nimble.ui.main.questions.KEY_DATA"
        const val HIGH_IMAGE_QUALITY = "l"
        fun newInstance(id: String) =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_DATA, id)
                }
            }
    }

    private var surveyId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            surveyId = it.getString(KEY_DATA)
        }
    }

    override fun initUI() {
    }

    override fun initViewModel() {
    }
}