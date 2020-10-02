package com.boltic28.pushnotification.service

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging

class FBIService() : FirebaseInstanceIdService() {

    companion object{
        const val TAG = "19991"
    }

    override fun onTokenRefresh() {
        val refreshedToken: String = FirebaseInstanceId.getInstance().getToken()!!
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        sendToServer(refreshedToken)
    }

    fun sendToServer(token: String){
        Log.d(TAG, token)
    }
}