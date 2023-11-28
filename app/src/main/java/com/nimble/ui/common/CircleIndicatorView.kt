package com.nimble.ui.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.forEachIndexed
import com.nimble.R

/**
 * @file SurveyProgressView.kt
 * @date 11/28/2023
 * @brief
 * @copyright
 */
class CircleIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    private var indicatorContainer: LinearLayout

    init {
        indicatorContainer = LinearLayout(context)
        indicatorContainer.orientation = LinearLayout.HORIZONTAL
        indicatorContainer.gravity = Gravity.START
        addView(indicatorContainer)
    }

    fun setCircleCount(count: Int) {
        indicatorContainer.removeAllViews()

        for (i in 0 until count) {
            val circle = createCircleIndicator()
            indicatorContainer.addView(circle)
        }
    }

    fun setSelectedPosition(position: Int) {
        indicatorContainer.forEachIndexed { index, view ->
            indicatorContainer.addView(createCircleIndicator(), index)
        }

        indicatorContainer.addView(createSelectedCircleIndicator(), position)
        smoothScrollToPosition(position)
    }

    private fun createCircleIndicator(): AppCompatImageView {
        val circleSize = dpToPx(16) // Set your desired size
        val circleMargin = dpToPx(4) // Set your desired margin

        val circle = AppCompatImageView(context)
        val params = LinearLayout.LayoutParams(circleSize, circleSize)
        params.setMargins(circleMargin, 0, circleMargin, 0)
        circle.layoutParams = params
        circle.setImageResource(R.drawable.circle_unselected) // Create a drawable for unselected state

        return circle
    }

    private fun createSelectedCircleIndicator(): AppCompatImageView {
        val circleSize = dpToPx(16) // Set your desired size
        val circleMargin = dpToPx(4) // Set your desired margin

        val circle = AppCompatImageView(context)
        val params = LinearLayout.LayoutParams(circleSize, circleSize)
        params.setMargins(circleMargin, 0, circleMargin, 0)
        circle.layoutParams = params
        circle.setImageResource(R.drawable.circle_selected) // Create a drawable for unselected state
        circle.setColorFilter(Color.WHITE)

        circle.setOnClickListener {
            // Handle click event, e.g., scroll to the selected position
            val position = indicatorContainer.indexOfChild(it)
            smoothScrollToPosition(position)
        }

        return circle
    }

    private fun smoothScrollToPosition(position: Int) {
        val targetScrollX = position * (width / indicatorContainer.childCount)
        smoothScrollTo(targetScrollX, 0)
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}