package com.nimble.ui.auth.ui.forgotpassword

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.Resource
import com.nimble.databinding.FragmentForgotPasswordBinding
import com.nimble.databinding.FragmentLoginBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.ui.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @file LoginFragment
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(R.layout.fragment_forgot_password) {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        binding.btnLogIn.setOnClickListener {
            viewModel.login(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
        }
        binding.btnRegister.setOnClickListener {
            getCurrentActivity<AuthActivity>()?.startRegisterFragment()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewModel.authLoginResponse.observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Resource.Status.LOADING -> {
                    showWaiting()
                }

                Resource.Status.SUCCESS -> {
                    dismissWaiting()

                    getCurrentActivity<AuthActivity>()?.startMainActivity()
                }

                Resource.Status.ERROR -> {
                    dismissWaiting()

                    data.data?.let {
                        it.errors?.firstOrNull()?.detail?.let { showError(it) }
                    }
                }
            }
        }
    }

}