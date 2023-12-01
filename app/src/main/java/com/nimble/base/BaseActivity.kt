package com.nimble.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nimble.ui.common.ProgressDialog
import java.io.Serializable

/**
 * @file BaseActivity
 * @date 11/25/2023
 * @brief Include common functions for BaseActivity class
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
): AppCompatActivity() {

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

    inline fun <reified T : Any> Context.startActivity(vararg extras: Pair<String, Any?>) {
        val intent = Intent(this, T::class.java)
        if (extras.isNotEmpty()) {
            val bundle = Bundle().apply {
                for ((key, value) in extras) {
                    when (value) {
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is CharSequence -> putCharSequence(key, value)
                        is Parcelable -> putParcelable(key, value)
                        is Serializable -> putSerializable(key, value)
                    }
                }
            }
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    fun FragmentActivity.startFragment(
        containerId: Int,  // The ID of the container where the fragment will be placed
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.slide_in_left,  // Enter animation
                android.R.anim.slide_out_right,  // Exit animation
                android.R.anim.slide_in_left,  // Pop enter animation
                android.R.anim.slide_out_right  // Pop exit animation
            )

            replace(containerId, fragment, tag)

            if (addToBackStack) {
                addToBackStack(tag)
            }
        }.commit()
    }

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