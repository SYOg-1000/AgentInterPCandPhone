package com.agentinter.pcphone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.agentinter.pcphone.chat.ChatScreen
import com.agentinter.pcphone.chat.ChatViewModel
import com.agentinter.pcphone.core.config.AppConfig
import com.agentinter.pcphone.core.config.ConfigProvider
import com.agentinter.pcphone.core.network.GatewayClient
import com.agentinter.pcphone.core.network.SessionManager
import com.agentinter.pcphone.core.storage.AppDatabase
import com.agentinter.pcphone.settings.SettingsScreen
import com.agentinter.pcphone.ui.theme.AgentInterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val configProvider = ConfigProvider(applicationContext)

        setContent {
            val config by configProvider.config.collectAsState(initial = AppConfig())
            var showSettings by remember {
                mutableStateOf(config.gatewayToken.isBlank())
            }

            AgentInterTheme {
                if (showSettings) {
                    SettingsScreen(
                        config = config,
                        configProvider = configProvider,
                        onBack = { showSettings = false }
                    )
                } else {
                    // key 鍩轰簬閰嶇疆鍐呭锛岄厤缃彉鏇存椂寮哄埗閲嶅缓 client/vm
                    val configKey = "${config.gatewayHost}:${config.gatewayPort}:${config.gatewayToken}"
                    key(configKey) {
                        val client = remember {
                            GatewayClient(config.gatewayHost, config.gatewayPort, config.gatewayToken)
                        }
                        val sessionManager = remember { SessionManager(client) }
                        val database = remember { AppDatabase.getInstance(applicationContext) }
                        val chatViewModel = remember { ChatViewModel(client, sessionManager, database) }

                        ChatScreen(
                            viewModel = chatViewModel,
                            onOpenSettings = { showSettings = true }
                        )
                    }
                }
            }
        }
    }
}
