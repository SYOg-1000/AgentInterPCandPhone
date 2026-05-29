package com.agentinter.pcphone.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.agentinter.pcphone.core.config.AppConfig
import com.agentinter.pcphone.core.config.ConfigProvider
import kotlinx.coroutines.launch
import java.net.NetworkInterface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    config: AppConfig,
    configProvider: ConfigProvider,
    onBack: () -> Unit
) {
    var host by remember(config) { mutableStateOf(config.gatewayHost) }
    var port by remember(config) { mutableStateOf(config.gatewayPort.toString()) }
    var token by remember(config) { mutableStateOf(config.gatewayToken) }
    var saved by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 鑾峰彇鏈満灞€鍩熺綉 IP锛堟彁绀虹敤锛?    val lanIp = remember { getLanIp() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Gateway 璁剧疆") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "杩斿洖")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 鈹€鈹€ 鎻愮ず鍗＄墖 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("馃挕 杩炴帴鏂瑰紡", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "鍚屼竴 WiFi锛氬～鐢佃剳鐨勫眬鍩熺綉 IP\n" +
                        "Tailscale锛氬～鐢佃剳鐨?Tailscale IP\n" +
                        "鍏綉锛氬～鍏綉 IP + 绔彛杞彂",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (lanIp != null) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "鏈満灞€鍩熺綉 IP锛?lanIp",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // 鈹€鈹€ Host 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            OutlinedTextField(
                value = host,
                onValueChange = { host = it; saved = false },
                label = { Text("涓绘満鍦板潃") },
                placeholder = { Text("192.168.1.5 鎴?tailscale-ip") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 鈹€鈹€ Port 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            OutlinedTextField(
                value = port,
                onValueChange = { port = it; saved = false },
                label = { Text("绔彛") },
                placeholder = { Text("18789") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // 鈹€鈹€ Token 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            OutlinedTextField(
                value = token,
                onValueChange = { token = it; saved = false },
                label = { Text("Token") },
                placeholder = { Text("Gateway 璁よ瘉 Token") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 鈹€鈹€ 淇濆瓨 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            Button(
                onClick = {
                    scope.launch {
                        val portNum = port.toIntOrNull() ?: 18789
                        configProvider.updateGateway(host.trim(), portNum, token.trim())
                        saved = true
                        snackbarHostState.showSnackbar("宸蹭繚瀛橈紝涓嬫鍚姩鐢熸晥")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = host.isNotBlank() && port.isNotBlank()
            ) {
                Text(if (saved) "鉁?宸蹭繚瀛? else "淇濆瓨閰嶇疆")
            }

            Spacer(Modifier.height(8.dp))

            HorizontalDivider()

            // 鈹€鈹€ 鏆楄壊妯″紡 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            Text("涓婚", style = MaterialTheme.typography.titleMedium)
            Text(
                "璺熼殢绯荤粺",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** 鑾峰彇鏈満灞€鍩熺綉 IPv4 鍦板潃 */
private fun getLanIp(): String? {
    return try {
        NetworkInterface.getNetworkInterfaces()?.asSequence()?.flatMap { ifc ->
            ifc.inetAddresses.asSequence()
        }?.firstOrNull { addr ->
            !addr.isLoopbackAddress && addr.hostAddress?.contains(':') == false
        }?.hostAddress
    } catch (_: Exception) {
        null
    }
}
