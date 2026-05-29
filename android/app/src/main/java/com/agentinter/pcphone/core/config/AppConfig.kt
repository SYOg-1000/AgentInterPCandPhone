package com.agentinter.pcphone.core.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class AppConfig(
    val gatewayHost: String = "127.0.0.1",
    val gatewayPort: Int = 18789,
    val gatewayToken: String = "",
    val darkMode: DarkMode = DarkMode.System
)

enum class DarkMode { Light, Dark, System }

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ConfigProvider(private val context: Context) {

    companion object {
        private val KEY_HOST = stringPreferencesKey("gateway_host")
        private val KEY_PORT = intPreferencesKey("gateway_port")
        private val KEY_TOKEN = stringPreferencesKey("gateway_token")
        private val KEY_DARK_MODE = stringPreferencesKey("dark_mode")
    }

    /** 閰嶇疆娴?鈥?Compose 鍙洿鎺?collectAsState */
    val config: Flow<AppConfig> = context.dataStore.data.map { prefs ->
        AppConfig(
            gatewayHost = prefs[KEY_HOST] ?: "127.0.0.1",
            gatewayPort = prefs[KEY_PORT] ?: 18789,
            gatewayToken = prefs[KEY_TOKEN] ?: "",
            darkMode = when (prefs[KEY_DARK_MODE]) {
                "light" -> DarkMode.Light
                "dark" -> DarkMode.Dark
                else -> DarkMode.System
            }
        )
    }

    /** 鏇存柊 Gateway 閰嶇疆 */
    suspend fun updateGateway(host: String, port: Int, token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_HOST] = host
            prefs[KEY_PORT] = port
            prefs[KEY_TOKEN] = token
        }
    }

    /** 鏇存柊鏆楄壊妯″紡 */
    suspend fun updateDarkMode(mode: DarkMode) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DARK_MODE] = when (mode) {
                DarkMode.Light -> "light"
                DarkMode.Dark -> "dark"
                else -> "system"
            }
        }
    }
}
