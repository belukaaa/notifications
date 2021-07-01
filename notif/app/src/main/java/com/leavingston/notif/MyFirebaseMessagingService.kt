package com.leavingston.notif

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "channeld_id"
    private val notification_id = 101

    fun createChannel (p0: RemoteMessage){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel= NotificationChannel(CHANNEL_ID , p0.notification?.title , NotificationManager.IMPORTANCE_HIGH ).apply {
                description = p0.notification?.body
        }

            val notManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notManager.createNotificationChannel(channel)

        }

    }


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("token" , "token is here $p0")
    }


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.i("message" , "message is here ${p0.notification?.body}")

//        if (p0.data.size > 0) {
//            val title = p0.data["title"]
//            val body = p0.data["body"]
//            showNotification(applicationContext, title, body)
//        } else {
//            val title = p0.notification!!.title
//            val body = p0.notification!!.body
//            showNotification(applicationContext, title, body)
//        }
        createChannel(p0)
        sendnotification(p0)
    }
    fun sendnotification(p0: RemoteMessage){
        val intent = Intent(this , MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_ONE_SHOT )
        val bitmab = BitmapFactory.decodeResource(applicationContext.resources , R.drawable.wifi)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this , CHANNEL_ID)
            .setLargeIcon(bitmab)
            .setColor(resources.getColor(R.color.trawakuamawakua))
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(p0.notification?.title)
            .setContentText("ჩასქროლე აბა ბრატ")
            .setStyle(NotificationCompat.BigTextStyle().bigText(p0.notification?.body))
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 , builder.build())




    }



}