package com.nimble.ui.auth.ui.forgotpassword

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.Resource
import com.nimble.databinding.FragmentForgotPasswordBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.ui.AuthViewModel
import com.nimble.utils.ValidatorUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * @file LoginFragment
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(R.layout.fragment_forgot_password) {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        binding.btnReset.setOnClickListener {
            reset()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewModel.authResetResponse.observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Resource.Status.LOADING -> {
                    showWaiting()
                }

                Resource.Status.SUCCESS -> {
                    dismissWaiting()

                    val message = data.data?.meta?.message
                    message?.let { showSuccess(it) }

                    getCurrentActivity<AuthActivity>()?.onBackPressed()
                }

                Resource.Status.ERROR -> {
                    dismissWaiting()
                }
            }
        }
    }

    private fun reset() {
        val email = binding.editTextEmail.text.toString()

        val validator = ValidatorUtil()

        if (!validator.isEmailValid(email)) {
            showError(getString(R.string.error_valid_email))
            return
        }

        viewModel.reset(email)

    }

}