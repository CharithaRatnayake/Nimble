package com.nimble.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nimble.ui.common.ProgressDialog

/**
 * @file BaseFragment
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    lateinit var binding: T
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        initViewModel()
        initUI()

        return binding.root
    }

    abstract fun initUI()

    abstract fun initViewModel()

    fun showWaiting() {
        if (!::mProgressDialog.isInitialized) {
            mProgressDialog = ProgressDialog()
        }

        if (!mProgressDialog.isVisible) {
            mProgressDialog.show(parentFragmentManager, ProgressDialog::class.java.simpleName)
        }
    }

    fun dismissWaiting() {
        if (::mProgressDialog.isInitialized) {
            if (mProgressDialog.isVisible) {
                mProgressDialog.dismiss()
            }
        }
    }
}