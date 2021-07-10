package com.example.shikha.collegegossip.Notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.icu.text.CaseMap
import android.net.Uri
import android.os.Build

class OreoNotifiaction(base:Context?): ContextWrapper(base)
{
    private var notifiactionManager: NotificationManager? = null

    companion object{
        private  const val  CHANNEL_ID ="com.example.shikha.collegegossip"

        private const val CHANNEL_NAME = "College Gossip"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel(){
        val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableLights(false)
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager!!.createNotificationChannel(channel)
    }

    val getManager: NotificationManager? get() {
        if (notifiactionManager == null){
            notifiactionManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notifiactionManager
    }
    @TargetApi(Build.VERSION_CODES.O)

    fun getOreoNotification(
            title: String?,
            body: String?,
            pendingIntent: PendingIntent?
            ,soundUri: Uri,
            icon :String?) : Notification.Builder
    {
        return Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(icon!!.toInt())
                .setSound(soundUri)
                .setAutoCancel(true)
    }
}