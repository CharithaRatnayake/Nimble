package com.nimble.ui.main

import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.UserDataModel
import com.nimble.databinding.ActivitySliderBinding
import com.nimble.ui.main.surveys.SurveysListFragment
import com.nimble.ui.main.survey.SurveyActivity
import com.nimble.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseActivity<ActivitySliderBinding>(R.layout.activity_slider) {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.slider, menu)
        return true
    }

    override fun initialize() {

        startFragment(R.id.container, SurveysListFragment.newInstance(), false)
        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_profile)
            .setOnClickListener {
                Log.d(javaClass.simpleName, "drawer_profile click event")
            }
    }

    fun loadProfileData(data: UserDataModel) {
        val imageView = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_profile)
        val textView = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.drawer_title)

        Helper.loadCircleImageView(imageView.context, imageView, data.avatarUrl)
        textView.text = data.name
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.END)
    }

    fun startSurveyInfoActivity(surveyAttributeDataModel: SurveyAttributeDataModel) {
        startActivity<SurveyActivity>(Pair(SurveyActivity.KEY_DATA, surveyAttributeDataModel))
    }
}