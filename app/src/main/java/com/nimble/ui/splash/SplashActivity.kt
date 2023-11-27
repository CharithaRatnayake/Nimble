package com.nimble.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivitySplashBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.main.SliderActivity
import com.nimble.utils.Base64Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(0) {

    private lateinit var viewModel: SplashViewModel

    override fun initialize() {

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity<SliderActivity>()
            } else {
                startActivity<AuthActivity>()
            }

            finish()
        }
        viewModel.getCacheData()
    }
}