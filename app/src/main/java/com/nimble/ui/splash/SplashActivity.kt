package com.nimble.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivitySplashBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.main.SliderActivity
import com.nimble.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private lateinit var viewModel: SplashViewModel
    private val handler = Handler(Looper.getMainLooper())

    override fun initialize() {

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity<SliderActivity>()

            } else {

                startActivity<AuthActivity>()

//                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    this@SplashActivity, Pair(binding.logo, "fade")
//                )
//                startActivity(intent, options.toBundle())
            }

            handler.postDelayed({
                finish()
            }, 1000L)
        }
        viewModel.getCacheData()
    }
}