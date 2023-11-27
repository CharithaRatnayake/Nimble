package com.nimble.ui

import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.descriptionText
import com.mikepenz.materialdrawer.model.interfaces.iconRes
import com.mikepenz.materialdrawer.model.interfaces.nameText
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivityMainBinding
import com.nimble.ui.main.surveys.SurveysListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initialize() {

        AccountHeaderView(this).apply {
            attachToSliderView(binding.slider) // attach to the slider
            addProfiles(
                ProfileDrawerItem().apply { nameText = "Mike Penz"; descriptionText = "mikepenz@gmail.com"; iconRes = R.drawable.ic_logo; identifier = 102 }
            )
            onAccountHeaderListener = { view, profile, current ->
                // react to profile changes
                false
            }
        }

        startFragment(R.id.container, SurveysListFragment.newInstance())
    }
}