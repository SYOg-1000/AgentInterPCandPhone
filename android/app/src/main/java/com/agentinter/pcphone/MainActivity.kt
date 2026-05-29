package com.agentinter.pcphone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.agentinter.pcphone.chat.ChatScreen
import com.agentinter.pcphone.chat.ChatViewModel
import com.agentinter.pcphone.core.config.AppConfig
import com.agentinter.pcphone.core.network.GatewayClient
import com.agentinter.pcphone.core.network.SessionManager
import com.agentinter.pcphone.core.storage.AppDatabase
import com.agentinter.pcphone.ui.theme.AgentInterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 閰嶇疆纭紪鐮侊紙鍚庣画鎺ュ叆 DataStore 鎸佷箙鍖?+ QR 鐮佹壂鎻忛厤缃級
        val config = AppConfig(
            gatewayHost = "127.0.0.1",
            gatewayPort = 18789,
            gatewayToken = "7449027094096872e0f93481dafab93bc06034cd524aa3a5"
        )

        val client = GatewayClient(
            host = config.gatewayHost,
            port = config.gatewayPort,
            token = config.gatewayToken
        )
        val sessionManager = SessionManager(client)
        val database = AppDatabase.getInstance(applicationContext)
        val chatViewModel = ChatViewModel(client, sessionManager, database)

        setContent {
            AgentInterTheme {
                ChatScreen(viewModel = chatViewModel)
            }
        }
    }
}
