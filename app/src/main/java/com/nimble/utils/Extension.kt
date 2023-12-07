package com.nimble.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.nimble.R
import dagger.hilt.internal.Preconditions
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extension function to start an activity of type [T] with optional extras.
 *
 * @param extras Vararg of key-value pairs representing extra data to be passed to the activity.
 *               The values can be of types: Int, Long, CharSequence, Parcelable, Serializable.
 */
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

/**
 * Extension function to start a [Fragment] in a [FragmentActivity].
 *
 * @param containerId The ID of the container where the fragment will be placed.
 * @param fragment The [Fragment] to be added or replaced.
 * @param addToBackStack Indicates whether the transaction should be added to the back stack.
 * @param tag A string tag for identifying the fragment in the back stack.
 */
fun FragmentActivity.startFragment(
    containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String? = null
) {
    supportFragmentManager.beginTransaction().apply {

        replace(containerId, fragment, tag)

        if (addToBackStack) {
            addToBackStack(tag)
        }
    }.commit()
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

fun Float.dpToPx(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

fun Float.spToPxFnp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    this,
    context.resources.displayMetrics
)

/**
 * Formats the current date and time based on the provided [format].
 *
 * @param format The format pattern to apply to the date and time (e.g., "yyyy-MM-dd HH:mm:ss").
 * @return A formatted string representing the current date and time.
 */
fun Date.getDateAndTimeForFormat(format: String): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(currentDate)
}

/**
 * Extension function to load a circular image into an ImageView using Glide.
 *
 * @param url The URL or resource of the image to load.
 */
fun ImageView?.loadCircleImage(url: String) {
    this?.let {
        Glide.with(it.context)
            .load(url) // Replace with your image URL or resource
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .placeholder(R.drawable.ic_placeholder_profile) // Replace with your placeholder image
            .into(it)
    }
}

/**
 * Extension function to load an image into an ImageView using Glide.
 *
 * @param url The URL or resource of the image to load.
 */
fun ImageView?.loadImageView(url: String) {
    this?.let {
        Glide.with(it.context)
            .load(url) // Replace with your image URL or resource
            .apply(RequestOptions().centerCrop())
            .placeholder(R.drawable.background_gradient_survey) // Replace with your placeholder image
            .into(it)
    }
}

/**
 * Converts a UTF-8 string to a Base64-encoded string.
 *
 * @param input The UTF-8 string to encode.
 * @return The Base64-encoded string.
 */
fun String.stringToBase64(input: String): String {
    val bytes = input.toByteArray(Charsets.UTF_8)
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

/**
 * Converts a Base64-encoded string to a regular UTF-8 string.
 *
 * @param base64String The Base64-encoded string to decode.
 * @return The decoded UTF-8 string.
 */
fun String.base64ToString(base64String: String): String {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    return String(decodedBytes, Charsets.UTF_8)
}

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.MainTheme,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )
    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()
        fragment.action()
    }
}
