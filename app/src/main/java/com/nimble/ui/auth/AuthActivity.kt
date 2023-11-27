package com.nimble.ui.auth

import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivityAuthBinding
import com.nimble.ui.auth.forgotpassword.ForgotPasswordFragment
import com.nimble.ui.auth.login.LoginFragment
import com.nimble.ui.auth.register.RegisterFragment
import com.nimble.ui.main.SliderActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {

    override fun initialize() {

        startFragment(R.id.container, LoginFragment.newInstance())

    }

    fun startRegisterFragment() {
        startFragment(R.id.container, RegisterFragment.newInstance(), true)
    }

    fun startForgotPasswordFragment() {
        startFragment(R.id.container, ForgotPasswordFragment.newInstance(), true)
    }

    fun startMainActivity() {
        startActivity<SliderActivity>()

        finish()
    }
}