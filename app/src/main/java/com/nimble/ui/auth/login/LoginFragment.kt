package com.nimble.ui.auth.login

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.http.Resource
import com.nimble.databinding.FragmentLoginBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
import com.nimble.utils.ValidatorUtil
import dagger.hilt.android.AndroidEntryPoint


/**
 * @file LoginFragment
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        val slideInFromCenter =
            AnimatorInflater.loadAnimator(binding.logo.context, R.animator.slide_in_from_center) as AnimatorSet

        // Apply the animation to the ImageView

        // Apply the animation to the ImageView
        slideInFromCenter.setTarget(binding.logo)
        slideInFromCenter.start()

        binding.btnLogIn.setOnClickListener {
            login()
        }
        binding.btnRegister.setOnClickListener {
            getCurrentActivity<AuthActivity>()?.startRegisterFragment()
        }
        binding.btnReset.setOnClickListener {
            getCurrentActivity<AuthActivity>()?.startForgotPasswordFragment()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewModel.authLoginResponse.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {
                    showWaiting()
                }

                is Resource.Success -> {
                    dismissWaiting()

                    getCurrentActivity<AuthActivity>()?.startMainActivity()
                }

                is Resource.Failure -> {
                    dismissWaiting()

                    showError(data)
                }
            }
        }
    }

    private fun login() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        val validator = ValidatorUtil()

        if (!validator.isEmailValid(email)) {
            showError(getString(R.string.error_valid_email))
            return
        }
        if (!validator.isPasswordValid(password)) {
            showError(getString(R.string.error_valid_password))
            return
        }

        viewModel.login(
            email, password
        )
    }

}