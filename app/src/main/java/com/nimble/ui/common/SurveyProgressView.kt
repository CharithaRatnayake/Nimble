package com.nimble.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.gson.Gson
import com.nimble.R
import com.nimble.data.http.AppError
import com.nimble.data.http.Resource
import com.nimble.databinding.ViewSurveysProgressBinding
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * @file SurveyProgressView.kt
 * @date 11/28/2023
 * @brief
 * @copyright
 */
class SurveyProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var binding: ViewSurveysProgressBinding
    var onClickRetryListener: (() -> Unit)? = null

    init {
        binding =
            ViewSurveysProgressBinding.inflate(LayoutInflater.from(context), this, true)
        binding.buttonRetry.setOnClickListener {
            startAnimation()
            binding.errorView.visibility = GONE

            onClickRetryListener?.invoke()
        }
    }

    fun startAnimation() {
        binding.shimmerProfile.startShimmerAnimation()
        binding.progressView.visibility = VISIBLE
    }

    fun stopAnimation() {
        binding.shimmerProfile.stopShimmerAnimation()
        binding.progressView.visibility = GONE
    }

    fun setError(data: Resource.Failure) {
        stopAnimation()

        val message = when {
            data.isNetworkError -> context.getString(R.string.error_unable_to_connect)
            data.errorCode == 404 -> context.getString(R.string.error_server_not_found)
            data.errorBody != null -> {
                val errors = data.errorBody.getErrorObject<AppError>().errors
                if (errors?.isNotEmpty() == true) errors[0].detail
                else context.getString(R.string.error_unknown_error)
            }
            else -> context.getString(R.string.error_unknown_error)
        }

        binding.errorText.text = message

        binding.errorView.visibility = VISIBLE
    }

    private inline fun <reified T> ResponseBody.getErrorObject(): T {
        val gson = Gson()
        val jsonObject = JSONObject(charStream().readText())
        return gson.fromJson(jsonObject.toString(), T::class.java)
    }
}