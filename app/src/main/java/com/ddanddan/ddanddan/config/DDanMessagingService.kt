package com.ddanddan.ddanddan.config

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.domain.ddanddanDataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class DDanMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStore: ddanddanDataStore

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        if (token != dataStore.deviceToken) {
            dataStore.deviceToken = token
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            it.title?.let { title ->
                val msg = Message(title, message.notification?.body ?: "")
                sendNotificationAlarm(msg)
            }
        }
    }

    private fun sendNotificationAlarm(message: Message) {
        val notifyId = Random().nextInt()
        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            notifyId,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )

        val channelId = getString(R.string.ddan_notification_channel_id)
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId).setSmallIcon(R.mipmap.ic_launcher)
                .apply {
                    setContentTitle(message.title).setContentText(message.body)
                    setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
                    setContentIntent(pendingIntent)
                    setDefaults(NotificationCompat.DEFAULT_ALL)
                    setCategory(NotificationCompat.CATEGORY_MESSAGE)
                }

        val notificationManager = getSystemService<NotificationManager>()
        val channel = NotificationChannel(
            channelId,
            channelId,
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = getString(R.string.ddan_notification_channel_description)
            enableLights(true)
            enableVibration(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        notificationManager?.run {
            createNotificationChannel(channel)
            notify(notifyId, notificationBuilder.build())
        }
    }

    data class Message(
        val title: String,
        val body: String,
    )
}