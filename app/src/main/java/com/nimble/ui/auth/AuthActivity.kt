package com.nimble.ui.auth

import com.nimble.R
import com.nimble.base.BaseActivity
import com.nimble.databinding.ActivityAuthBinding
import com.nimble.ui.auth.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {

    override fun initialize() {

        startFragment(R.id.container, LoginFragment.newInstance())

    }
}