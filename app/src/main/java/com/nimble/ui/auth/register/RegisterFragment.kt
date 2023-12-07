package com.nimble.ui.auth.register

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.http.Resource
import com.nimble.databinding.FragmentRegisterBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
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

        viewModel.validateCredentials(name, email, password, rePassword)
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

                else -> {}
            }
        }
        // Observe the isValidCredentials LiveData in the ViewModel
        viewModel.isValidCredentials.observe(viewLifecycleOwner) { status ->
            if (status.first) {
                val name = binding.editTextName.text.toString()
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                //Call register api
                viewModel.register(
                    name, email, password
                )
            } else {
                showError(getString(status.second))
            }
        }
    }
}