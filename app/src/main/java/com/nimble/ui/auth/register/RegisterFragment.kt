package com.nimble.ui.auth.register

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.http.Resource
import com.nimble.databinding.FragmentRegisterBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
import com.nimble.utils.ValidatorUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * @file RegisterFragment
 * @date 11/26/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val rePassword = binding.editTextRetypePassword.text.toString()

        val validator = ValidatorUtil()

        if (!validator.isNameValid(name)) {
            showError(getString(R.string.error_valid_name))
            return
        }
        if (!validator.isEmailValid(email)) {
            showError(getString(R.string.error_valid_email))
            return
        }
        if (!validator.isPasswordValid(password)) {
            showError(getString(R.string.error_valid_password))
            return
        }
        if (!validator.doPasswordsMatch(password, rePassword)) {
            showError(getString(R.string.error_valid_password_retype))
            return
        }

        //Call register api
        viewModel.register(
            name, email, password
        )
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
    }
}