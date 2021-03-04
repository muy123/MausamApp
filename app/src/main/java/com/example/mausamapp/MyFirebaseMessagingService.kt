package com.example.mausamapp

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// this class is for sending notifications
class MyFirebaseMessagingService:FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        getMessageReady(p0.notification?.title, p0.notification?.body)
    }

    private fun getMessageReady(title: String?, body: String?) {
        val myIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0)

        var builder = NotificationCompat.Builder(this, "MY_ID")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        var manager=NotificationManagerCompat.from(this)
        manager.notify(101,builder.build())
    }
}