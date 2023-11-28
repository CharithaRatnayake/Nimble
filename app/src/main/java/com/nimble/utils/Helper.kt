package com.nimble.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.nimble.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * @file Helper
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
class Helper {
    companion object {
        fun loadCircleImageView(context: Context, imageView: ImageView?, url: String) {
            imageView?.let {
                Glide.with(context)
                    .load(url) // Replace with your image URL or resource
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .placeholder(R.drawable.ic_placeholder_profile) // Replace with your placeholder image
                    .into(it)
            }
        }

        fun loadImageView(context: Context, imageView: ImageView?, url: String) {
            imageView?.let {
                Glide.with(context)
                    .load(url) // Replace with your image URL or resource
                    .apply(RequestOptions().centerCrop())
                    .placeholder(R.drawable.background_gradient_survey) // Replace with your placeholder image
                    .into(it)
            }
        }

        fun getCurrentDateAndTime(): String {
            // Get the current date and time
            val currentDate = Date()

            // Define the desired date format
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())

            return dateFormat.format(currentDate)
        }
    }
}