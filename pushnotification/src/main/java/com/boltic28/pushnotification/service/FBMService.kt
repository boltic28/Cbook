package com.boltic28.pushnotification.service

import com.boltic28.pushnotification.config.Config
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FBMService: FirebaseMessagingService() {


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        p0.data?.let {
            getImage(p0)
        }
    }

    private fun getImage(msg: RemoteMessage){
        val data: Map<String, String> = msg.data
        Config.title = data["title"]
        Config.content = data["content"]
        Config.imageUrl = data["imageUrl"]
        Config.gameUrl = data["gameUrl"]
        //Create thread to fetch image from notification
        //Create thread to fetch image from notification

        msg.data.let {
//            val uiHandler = Handler(Looper.getMainLooper())
//            uiHandler.post(Runnable { // Get image from data Notification
//                Picasso.get()
//                    .load(Config.imageUrl)
//                    .into(target)
//            })
        }
    }

}