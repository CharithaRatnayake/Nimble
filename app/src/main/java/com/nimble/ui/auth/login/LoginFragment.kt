package com.nimble.ui.auth.login

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.http.Resource
import com.nimble.databinding.FragmentLoginBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
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
        // Observe the authLoginResponse LiveData in the ViewModel
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
        // Observe the isValidCredentials LiveData in the ViewModel
        viewModel.isValidCredentials.observe(viewLifecycleOwner) { status ->
            if (status.first) {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                //call login api
                viewModel.login(email, password)
            } else {
                showError(getString(status.second))
            }
        }
    }

    private fun login() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        viewModel.validateCredentials(email, password)
    }

}