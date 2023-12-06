package com.nimble.ui.auth.forgotpassword

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.http.Resource
import com.nimble.databinding.FragmentForgotPasswordBinding
import com.nimble.ui.auth.AuthActivity
import com.nimble.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @file ForgotPasswordFragment
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(R.layout.fragment_forgot_password) {

    companion object {
        const val SUCCESS_POPUP_DELAY = 2000L
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        binding.btnReset.setOnClickListener {
            reset()
        }
        binding.backButton.setOnClickListener {
            getCurrentActivity<AuthActivity>()?.onBackPressed()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        // Observe the authResetResponse LiveData in the ViewModel
        viewModel.authResetResponse.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {
                    showWaiting()
                }

                is Resource.Success -> {
                    dismissWaiting()

                    lifecycleScope.launch {
                        showSuccess(data.value.meta.message)
                        delay(SUCCESS_POPUP_DELAY)
                    }
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

                //call reset api
                viewModel.reset(email)
            } else {
                showError(getString(status.second))
            }
        }
    }

    private fun reset() {
        val email = binding.editTextEmail.text.toString()

        viewModel.validateCredentials(email)
    }

}