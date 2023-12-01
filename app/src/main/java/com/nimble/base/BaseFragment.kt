package com.nimble.base

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.akndmr.library.AirySnackbar
import com.akndmr.library.AirySnackbarSource
import com.akndmr.library.AnimationAttribute
import com.akndmr.library.GravityAttribute
import com.akndmr.library.IconAttribute
import com.akndmr.library.RadiusAttribute
import com.akndmr.library.SizeAttribute
import com.akndmr.library.SizeUnit
import com.akndmr.library.TextAttribute
import com.akndmr.library.Type
import com.google.android.material.snackbar.Snackbar
import com.nimble.R
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

    inline fun <reified T : Activity> getCurrentActivity(): T? {
        val activity: Activity? = activity
        if (activity is T) {
            return activity
        }
        return null
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

    fun showError(message: String) {
        val snackbar = AirySnackbar.make(
            source = AirySnackbarSource.ActivitySource(requireActivity()),
            type = Type.Error,
            attributes =
            listOf(
                TextAttribute.Text(text = message),
                TextAttribute.TextColor(textColor = R.color.white),
                IconAttribute.Icon(iconRes = R.drawable.ic_warning),
                IconAttribute.IconColor(iconTint = R.color.white),
                SizeAttribute.Margin(left = 4, right = 4, unit = SizeUnit.DP),
                SizeAttribute.Padding(top = 8, bottom = 8, left = 8, right = 8, unit = SizeUnit.DP),
                RadiusAttribute.Radius(radius = 2f),
                GravityAttribute.Top,
                AnimationAttribute.FadeInOut
            )
        )
        snackbar.show()
    }

    fun showSuccess(message: String) {
        val snackbar = AirySnackbar.make(
            source = AirySnackbarSource.ActivitySource(requireActivity()),
            type = Type.Success,
            attributes =
            listOf(
                TextAttribute.Text(text = message),
                TextAttribute.TextColor(textColor = R.color.white),
                IconAttribute.IconColor(iconTint = R.color.white),
                SizeAttribute.Margin(left = 4, right = 4, unit = SizeUnit.DP),
                SizeAttribute.Padding(top = 8, bottom = 8, left = 8, right = 8, unit = SizeUnit.DP),
                RadiusAttribute.Radius(radius = 2f),
                GravityAttribute.Top,
                AnimationAttribute.FadeInOut
            )
        )
        snackbar.show()
    }

    fun Snackbar.gravityTop() {
        this.view.layoutParams = (this.view.layoutParams as FrameLayout.LayoutParams).apply {
            gravity = Gravity.TOP
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            // Default status bar height if the resource is not found
            0
        }
    }
}