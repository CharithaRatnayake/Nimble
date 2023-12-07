package com.nimble

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * @file CustomTestRunner
 * @date 12/7/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/7/2023.
 */
class CustomTestRunner: AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}