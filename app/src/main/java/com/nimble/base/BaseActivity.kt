package com.nimble.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nimble.ui.common.ProgressDialog

/**
 * @file BaseActivity
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    lateinit var binding: T
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layoutResId != 0) {
            binding = DataBindingUtil.setContentView(this, layoutResId)
            binding.lifecycleOwner = this
        }
        initialize()
    }

    abstract fun initialize()

    fun showWaiting() {
        if (!::mProgressDialog.isInitialized) {
            mProgressDialog = ProgressDialog()
        }

        if (!mProgressDialog.isVisible) {
            mProgressDialog.show(supportFragmentManager, ProgressDialog::class.java.simpleName)
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