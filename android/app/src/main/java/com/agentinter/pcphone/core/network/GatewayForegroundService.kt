package com.agentinter.pcphone.core.network

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.agentinter.pcphone.core.notification.NotificationHelper

/**
 * 鍓嶅彴 Service 鈥?淇濇寔 WebSocket 闀胯繛鎺ュ湪鍚庡彴瀛樻椿銆? * 閫氱煡鏍忔樉绀?"AgentInter 路 宸茶繛鎺?銆? *
 * 鍚姩鏂瑰紡: ContextCompat.startForegroundService(context, Intent(context, GatewayForegroundService::class.java))
 */
class GatewayForegroundService : Service() {

    companion object {
        private const val TAG = "GatewayForegroundService"
        const val ACTION_STOP = "com.agentinter.pcphone.STOP_SERVICE"
    }

    private var gatewayClient: GatewayClient? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
        NotificationHelper.createChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            stopSelf()
            return START_NOT_STICKY
        }

        // 鍚姩鍓嶅彴閫氱煡
        val notification = NotificationHelper.buildConnectionNotification(this)
        startForeground(1, notification)

        // GatewayClient 搴旂敱 Application 鎸佹湁骞舵敞鍏ャ€傚綋鍓?Service 浠呯淮鎸佸墠鍙伴€氱煡銆?        Log.d(TAG, "Foreground service started")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        gatewayClient?.disconnect()
        gatewayClient = null
        NotificationHelper.cancelConnectionNotification(this)
        Log.d(TAG, "Service destroyed")
        super.onDestroy()
    }
}
