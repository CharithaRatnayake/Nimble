package com.nimble

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nimble.ui.auth.login.LoginFragment
import com.nimble.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @file AuthActivityTest
 * @date 12/6/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/6/2023.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    @UiThread
    fun validateUIPass() {
        val loginFragment = launchFragmentInHiltContainer<LoginFragment>() {
            assert(this.view?.findViewById<ImageView>(R.id.logo) != null)
            assert(this.view?.findViewById<LinearLayoutCompat>(R.id.layout_login) != null)
            assert(this.view?.findViewById<EditText>(R.id.edit_text_email) != null)
            assert(this.view?.findViewById<EditText>(R.id.edit_text_password) != null)
            assert(this.view?.findViewById<TextView>(R.id.btnReset) != null)
            assert(this.view?.findViewById<Button>(R.id.btn_log_in) != null)
            assert(this.view?.findViewById<TextView>(R.id.btn_register) != null)
        }
    }

    @Test
    @UiThread
    fun validateEdittextHintPass() {
        val loginFragment = launchFragmentInHiltContainer<LoginFragment>() {
            assert(this.view?.findViewById<EditText>(R.id.edit_text_email)?.hint == getString(R.string.email))
            assert(this.view?.findViewById<EditText>(R.id.edit_text_password)?.hint == getString(R.string.password))
            assert(this.view?.findViewById<TextView>(R.id.btnReset)?.text == getString(R.string.forgot))
            assert(this.view?.findViewById<Button>(R.id.btn_log_in)?.text == getString(R.string.register))
            assert(this.view?.findViewById<TextView>(R.id.btn_register)?.text == getString(R.string.create_new_nimble_account))
        }
    }
}