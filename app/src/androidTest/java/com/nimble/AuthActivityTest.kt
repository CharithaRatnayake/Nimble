package com.nimble

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nimble.ui.auth.AuthActivity
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @file AuthActivityTest
 * @date 12/6/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/6/2023.
 */
@RunWith(AndroidJUnit4::class)
class AuthActivityTest {

    @Test
    fun validateUIPass() {
        ActivityScenario.launch(AuthActivity::class.java)

        // Check fragment screen is displayed
        onView(withId(R.id.container)).check(matches(isDisplayed()))
    }
}