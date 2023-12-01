package com.nimble.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivitySplashBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.main.SliderActivity
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