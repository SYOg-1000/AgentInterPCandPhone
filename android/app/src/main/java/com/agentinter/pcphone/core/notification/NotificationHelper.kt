package com.agentinter.pcphone.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.agentinter.pcphone.MainActivity

/**
 * 閫氱煡绠＄悊宸ュ叿銆? * 璐熻矗閫氱煡娓犻亾鍒涘缓銆佹秷鎭€氱煡灞曠ず銆? */
object NotificationHelper {

    private const val CHANNEL_ID = "agentinter_messages"
    private const val CHANNEL_NAME = "娑堟伅閫氱煡"
    private const val NOTIFICATION_ID_CONNECTION = 1
    private const val NOTIFICATION_ID_MESSAGE = 1000

    /**
     * 鍒涘缓閫氱煡娓犻亾锛圓ndroid 8.0+ 蹇呴』锛夈€?     * 鍦?Application.onCreate 鎴?Service.onCreate 涓皟鐢ㄣ€?     */
    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "AgentInter 娑堟伅閫氱煡"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * 鍓嶅彴 Service 閫氱煡锛堜繚娲伙級銆?     * 鏄剧ず"AgentInter 路 宸茶繛鎺?銆?     */
    fun buildConnectionNotification(context: Context): android.app.Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("AgentInter")
            .setContentText("宸茶繛鎺?)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    /**
     * 鏂版秷鎭€氱煡銆?     * 鐐瑰嚮閫氱煡 鈫?鎵撳紑 MainActivity銆?     */
    fun showMessageNotification(context: Context, sender: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(sender)
            .setContentText(message.take(100))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(
            NOTIFICATION_ID_MESSAGE + System.currentTimeMillis().toInt().and(0xFFFF),
            notification
        )
    }

    /** 绉婚櫎杩炴帴閫氱煡 */
    fun cancelConnectionNotification(context: Context) {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID_CONNECTION)
    }
}
