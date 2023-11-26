package com.nimble.ui.auth.ui.login

import androidx.lifecycle.ViewModelProvider
import com.nimble.R
import com.nimble.base.BaseFragment
import com.nimble.data.AppResponses
import com.nimble.data.Resource
import com.nimble.data.TokenResponse
import com.nimble.databinding.FragmentLoginBinding
import com.nimble.ui.common.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun initUI() {
        binding.btnLogIn.setOnClickListener {
            viewModel.login("", "")
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
                }

                Resource.Status.ERROR -> {
                    dismissWaiting()
                }
            }
        }
    }

}