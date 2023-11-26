package com.nimble.ui

import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivitySplashBinding
import com.nimble.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(0) {
    override fun initialize() {
        startActivity<AuthActivity>()

        finish()
    }
}