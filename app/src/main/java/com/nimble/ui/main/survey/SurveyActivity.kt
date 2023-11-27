package com.nimble.ui.main.survey

import android.content.Intent
import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.databinding.ActivitySurveyBinding
import com.nimble.ui.main.surveys.SurveysListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : BaseActivity<ActivitySurveyBinding>(R.layout.activity_survey) {

    companion object {
        const val KEY_DATA = "com.nimble.ui.main.survey.KEY_DATA"

        fun newInstance() = SurveysListFragment()
    }

    override fun initialize() {
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val hasExtra = intent?.hasExtra(KEY_DATA) == true
        if (hasExtra) {
            val surveyAttributeDataModel =
                intent?.getSerializableExtra(KEY_DATA) as? SurveyAttributeDataModel

            surveyAttributeDataModel?.let {
                startFragment(R.id.container, SurveyFragment.newInstance(it))
            }
        }
    }
}