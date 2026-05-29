package com.agentinter.pcphone.core.config

data class AppConfig(
    val gatewayHost: String = "127.0.0.1",
    val gatewayPort: Int = 18789,
    val gatewayToken: String = "",
    val sessionKey: String = "main",
    val darkMode: DarkMode = DarkMode.System
)

enum class DarkMode {
    Light, Dark, System
}
