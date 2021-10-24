package com.amosh.vestiaireweatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.setIsVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun EditText.clickableOnly() {
    this.isFocusable = false
    this.isClickable = true
}

@Suppress("DEPRECATION") // Fixed for higher versions
fun Context?.isOffline(): Boolean {
    val connectivityManager =
        this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return true
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return true
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> false
            else -> true
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return true
        return !nwInfo.isConnected
    }
}

fun Long.isTomorrow(): Boolean = DateUtils.isToday(this - DateUtils.DAY_IN_MILLIS)
