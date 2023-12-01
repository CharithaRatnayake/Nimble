package com.nimble

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import android.widget.LinearLayout
import com.nimble.ui.common.CircleIndicatorView
import com.nimble.utils.stringToBase64
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor

/**
 * @file AuthViewModelTest
 * @date 12/1/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/1/2023.
 */

@ExperimentalCoroutinesApi
class CircleIndicatorTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var container: LinearLayout

    private val CIRCLE_SIZE = 20
    private val CIRCLE_SPACING = 4

    private lateinit var yourClassUnderTest: CircleIndicatorView

    @Before
    fun setup() {
        yourClassUnderTest = CircleIndicatorView(context)
    }

    @Test
    fun `addCircleView adds ImageView to the container with correct layout parameters`() {
        // Arrange
        val circleDrawable: Drawable = mock(Drawable::class.java)

        // Act
        yourClassUnderTest.addCircleView(circleDrawable)

        // Assert
        val captor = argumentCaptor<ImageView>()
        verify(container).addView(captor.capture())

        val capturedImageView = captor.firstValue
        val layoutParams = capturedImageView.layoutParams as LinearLayout.LayoutParams

        // Check that the layout parameters are set correctly
        assert(layoutParams.width == CIRCLE_SIZE)
        assert(layoutParams.height == CIRCLE_SIZE)
        assert(layoutParams.leftMargin == CIRCLE_SPACING)
        assert(layoutParams.rightMargin == CIRCLE_SPACING)
    }

    @Test
    fun `stringToBase64 returns base64 encoded string`() {
        // Arrange
        val yourClassUnderTest = String() // Replace with your actual class or object containing the function
        val inputString = "Hello, World!"

        // Act
        val result = yourClassUnderTest.stringToBase64(inputString)

        // Assert
        Assert.assertNotNull(result)
        Assert.assertNotEquals(
            inputString,
            result
        ) // Ensure the result is different from the original string

        // Check if decoding is successful (not throwing an exception)
        try {
            Base64.decode(result, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            Assert.fail("Decoding failed")
        }
    }

//    @Test
//    fun `stringToBase64 returns empty string for null input`() {
//        // Arrange
//        val yourClassUnderTest = String() // Replace with your actual class or object containing the function
//
//        // Act
//        val result = yourClassUnderTest.stringToBase64(null)
//
//        // Assert
//        assertNotNull(result)
//        assertTrue(result.isEmpty())
//    }

    @Test
    fun `stringToBase64 returns empty string for empty input`() {
        // Arrange
        val yourClassUnderTest = String() // Replace with your actual class or object containing the function

        // Act
        val result = yourClassUnderTest.stringToBase64("")

        // Assert
        Assert.assertNotNull(result)
        Assert.assertTrue(result.isEmpty())
    }
}