package com.nimble.ui.main

import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.data.SurveyAttributeDataModel
import com.nimble.data.UserDataModel
import com.nimble.databinding.ActivitySliderBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
import com.nimble.ui.main.survey.SurveyActivity
import com.nimble.ui.main.surveys.SurveysListFragment
import com.nimble.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseActivity<ActivitySliderBinding>(R.layout.activity_slider) {

    private lateinit var viewModel: AuthViewModel

    override fun initialize() {

        initViewModel()

        startFragment(R.id.container, SurveysListFragment.newInstance(), false)
        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_profile)
            .setOnClickListener {
                Log.d(javaClass.simpleName, "drawer_profile click event")
            }
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    logout()
                    true
                }

                else -> false
            }
        }
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewModel.authLogoutResponse.observe(this) { isLogout ->
            if (isLogout) {
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

    }

    private fun logout() {
        viewModel.logout()
    }

    fun loadProfileData(data: UserDataModel) {
        val imageView =
            binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_profile)
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