package com.boltic28.networkretroroom.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkChecker(private val context: Context) {

    fun checkInternetConnection(): Boolean{
        val conMng = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = conMng?.activeNetwork
        return network != null
    }
}