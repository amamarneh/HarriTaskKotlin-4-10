package com.example.mohammadamarneh.harritaskkotlin.binding

import androidx.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.mohammadamarneh.harritaskkotlin.network.NetworkConstants
import com.example.mohammadamarneh.harritaskkotlin.utils.DateUtils
import com.squareup.picasso.Picasso

/**
 * Data Binding adapters specific to the app.
 */
object DataBindingAdapters {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, imageUrl: String) {
        if (TextUtils.isEmpty(imageUrl)) {
            view.setImageURI(null)
        } else {
            Picasso.get().load(imageUrl).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("android:flag")
    fun setFlag(view: ImageView, countryCode: String?) {
        if (countryCode == null) view.setImageURI(null)
        else Picasso.get().load(NetworkConstants.getFlagUrl(countryCode)).into(view)
    }

    @JvmStatic
    @BindingAdapter("android:visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("android:date")
    fun dateToString(view: TextView, date: Long) {
        view.text = DateUtils.format(date)
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}