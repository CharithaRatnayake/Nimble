package com.nimble.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.nimble.R

/**
 * @file SurveyProgressView.kt
 * @date 11/28/2023
 * @brief
 * @copyright
 */
class CircleIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    companion object {
        private const val CIRCLE_SIZE = 20
        private const val CIRCLE_SPACING = 4
        private const val SMOOTH_SCROLL_DELAY = 100L
    }

    private val container: LinearLayout
    var position: Int = 0

    init {
        container = LinearLayout(context)
        container.orientation = LinearLayout.HORIZONTAL
        addView(container)
    }

    /**
     * Adds a specified number of circles to the container.
     *
     * @param size The number of circles to add.
     */
    fun addCircles(size: Int) {
        container.removeAllViews()
        for (i in 0 until size) {
            val circle = createUnSelectedCircleDrawable()
            addCircleView(circle)
        }
    }

    /**
     * Selects a circle at the specified position and updates the view accordingly.
     *
     * @param position The position of the circle to be selected.
     */
    fun selectCircles(position: Int) {
        this.position = position
        for (i in 0 until container.childCount) {
            val circle = container.getChildAt(i)
            setUnselectedDrawable(circle)
        }
        val circle = container.getChildAt(position)
        setSelectedDrawable(circle)

        scrollToCirclePosition(position)
    }

    private fun setUnselectedDrawable(circle: View?) {
        circle?.let { imageView ->
            (imageView as ImageView).setImageDrawable(createUnSelectedCircleDrawable())
        }
    }

    private fun setSelectedDrawable(circle: View?) {
        circle?.let { imageView ->
            (imageView as ImageView).setImageDrawable(createSelectedCircleDrawable())
        }
    }

    private fun createUnSelectedCircleDrawable(): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.circle_unselected)!!
    }

    private fun createSelectedCircleDrawable(): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.circle_selected)!!
    }

    /**
     * Adds a circle ImageView to the container with the specified circle drawable.
     *
     * @param circleDrawable The drawable representing the circle to be added.
     */
    fun addCircleView(circleDrawable: Drawable) {
        val circleImageView = ImageView(context)
        circleImageView.setImageDrawable(circleDrawable)

        val layoutParams = LayoutParams(CIRCLE_SIZE, CIRCLE_SIZE)
        layoutParams.setMargins(CIRCLE_SPACING, 0, CIRCLE_SPACING, 0)

        circleImageView.layoutParams = layoutParams

        // Add your circleImageView to the HorizontalScrollView
        container.addView(circleImageView)
    }

    /**
     * Scrolls the HorizontalScrollView to the specified circle position.
     *
     * @param position The position of the circle to scroll to.
     */
    private fun scrollToCirclePosition(position: Int) {
        // Delay the scroll to ensure the view is properly laid out
        Handler(Looper.getMainLooper()).postDelayed({
            smoothScrollTo(position * (CIRCLE_SIZE + CIRCLE_SPACING), 0)
        }, SMOOTH_SCROLL_DELAY)
    }
}