package com.agentinter.pcphone

import android.app.Application
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgentInterApp : Application() {

    companion object {
        private const val TAG = "AgentInterApp"
    }

    override fun onCreate() {
        super.onCreate()

        // 鍏ㄥ眬鏈崟鑾峰紓甯稿鐞嗗櫒 鈥?鍐欐棩蹇楁枃浠堕槻姝㈤潤榛橀棯閫€
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val logFile = File(filesDir, "crash.log")
            try {
                val writer = FileWriter(logFile, true)
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                writer.append("=== ${sdf.format(Date())} ===\n")
                writer.append("Thread: ${thread.name}\n")
                throwable.printStackTrace(PrintWriter(writer))
                writer.append("\n")
                writer.close()
                Log.e(TAG, "Crash logged to ${logFile.absolutePath}", throwable)
            } catch (_: Exception) {
                Log.e(TAG, "Failed to write crash log", throwable)
            }
            // 浜ょ粰绯荤粺榛樿澶勭悊鍣紙寮瑰嚭"宸插仠姝㈣繍琛?瀵硅瘽妗嗭級
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
