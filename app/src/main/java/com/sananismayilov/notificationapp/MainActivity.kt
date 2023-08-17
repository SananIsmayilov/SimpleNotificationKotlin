package com.sananismayilov.notificationapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript.Priority
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    lateinit var notification: NotificationCompat.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById(R.id.button) as Button

        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)

        button.setOnClickListener {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = Intent(this@MainActivity,MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(this@MainActivity,1,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val kanalid  = "kanalid"
                val kanalad = "kanalad"
                val kanaldescription = "kanaldesc"
                val kanalimp = NotificationManager.IMPORTANCE_HIGH

                var channel : NotificationChannel? = manager.getNotificationChannel(kanalid)

                if(channel == null){
                    channel = NotificationChannel(kanalid,kanalad,kanalimp)
                    channel.description = kanaldescription
                    manager.createNotificationChannel(channel)

                }
                notification = NotificationCompat.Builder(this@MainActivity,kanalid)
                notification.setAutoCancel(true)
                notification.setContentIntent(pendingIntent)
                notification.setContentTitle("Tittle")
                notification.setContentText("Text")
                notification.setSmallIcon(R.drawable.notification)


            }else {
                notification = NotificationCompat.Builder(this@MainActivity)
                notification.setAutoCancel(true)
                notification.setContentIntent(pendingIntent)
                notification.setContentTitle("Tittle")
                notification.setContentText("Text")
                notification.setSmallIcon(R.drawable.notification)
                notification.priority = Notification.PRIORITY_HIGH

            }

            manager.notify(1,notification.build())
        }


    }

}