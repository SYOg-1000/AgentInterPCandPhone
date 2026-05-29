package com.agentinter.pcphone.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agentinter.pcphone.core.network.ConnectionState

/**
 * 鑱婂ぉ涓荤晫闈? *
 * 鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹? * 鈹? TopAppBar                      鈹? * 鈹? AgentInter    馃煝 宸茶繛鎺?        鈹? * 鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹? * 鈹? [鍔犺浇鏇存棭鐨勬秷鎭痌  鈫?鏂板        鈹? * 鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹? * 鈹? LazyColumn (娑堟伅鍒楄〃)           鈹? * 鈹? 鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?  鈹? * 鈹? 鈹?       Hello!       [12:30]鈹? 鈹? * 鈹? 鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?  鈹? * 鈹? 鈹?[12:30] Hi there!        鈹?  鈹? * 鈹? 鈹?...                         鈹? * 鈹? 鈹?[typing indicator]          鈹? * 鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹? * 鈹? [TextField]              [鉃   鈹? * 鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹? *
 * @param viewModel ChatViewModel 瀹炰緥
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, onOpenSettings: () -> Unit = {}) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inputText by viewModel.inputText.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()
    val messages = uiState.messages

    // 鍥剧墖閫夋嫨鍣?    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val path = ImagePicker.compressAndCache(context, it)
            if (path != null) {
                viewModel.sendImage(path)
            }
        }
    }

    // 鏂囦欢閫夋嫨鍣?    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            val fileName = it.lastPathSegment ?: "file"
            viewModel.sendFile(it.toString(), fileName)
        }
    }

    // Slash 琛ュ叏
    val currentSlashWord = remember(inputText) {
        val lastSpace = inputText.lastIndexOf(' ')
        val word = if (lastSpace >= 0) inputText.substring(lastSpace + 1) else inputText
        if (word.startsWith("/")) word else ""
    }
    val completions = remember(currentSlashWord) {
        if (currentSlashWord.isNotEmpty()) SlashCompleter.complete(currentSlashWord) else emptyList()
    }
    var showCompletions by remember { mutableStateOf(false) }

    LaunchedEffect(completions) {
        showCompletions = completions.isNotEmpty()
    }

    // 鑷姩婊氬姩鍒板簳閮?    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // 閿欒 Snackbar
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("AgentInter") },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "璁剧疆")
                    }
                    // 杩炴帴鐘舵€?                    Text(
                        text = when (uiState.connectionStatus) {
                            ConnectionState.Connected    -> "\uD83D\uDFE2 宸茶繛鎺?
                            ConnectionState.Connecting   -> "\uD83D\uDFE1 杩炴帴涓?
                            ConnectionState.Reconnecting -> "\uD83D\uDFE1 閲嶈繛涓?
                            ConnectionState.Disconnected -> "\uD83D\uDD34 鏈繛鎺?
                        },
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 鈹€鈹€ 鍐呭鍖?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    // 鍔犺浇涓?& 鏃犳秷鎭?鈫?灞呬腑鍔犺浇鎸囩ず鍣?                    uiState.isLoading && messages.isEmpty() -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "鍔犺浇涓?..",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Session 鏈氨缁?& 涓嶅湪鍔犺浇 鈫?杩炴帴澶辫触閿欒椤?                    !uiState.sessionReady && !uiState.isLoading -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = uiState.error ?: "杩炴帴 Gateway 澶辫触",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.retryConnection() }) {
                                Text("閲嶈瘯杩炴帴")
                            }
                        }
                    }

                    // 浼氳瘽灏辩华浣嗘棤娑堟伅 鈫?绌虹姸鎬佹彁绀?                    messages.isEmpty() && uiState.sessionReady -> {
                        Text(
                            text = "鍙戦€佹秷鎭紑濮嬪璇?,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    // 姝ｅ父娑堟伅鍒楄〃
                    else -> {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            // 鍔犺浇鏇村鎸夐挳锛堝垪琛ㄩ《閮級
                            if (uiState.hasMore && messages.isNotEmpty()) {
                                item(key = "__load_more__") {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (uiState.isLoadingMore) {
                                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                        } else {
                                            TextButton(onClick = { viewModel.loadMore() }) {
                                                Text("鍔犺浇鏇存棭鐨勬秷鎭?)
                                            }
                                        }
                                    }
                                }
                            }

                            items(messages, key = { it.id }) { message ->
                                if (message.isReasoning) {
                                    ThinkingBubble(content = message.content)
                                } else {
                                    MessageItem(message = message)
                                }
                            }

                            // streaming indicator锛圓I 鍥炲涓級
                            if (uiState.isStreaming) {
                                item(key = "__streaming_indicator__") {
                                    TypingIndicator()
                                }
                            }
                        }
                    }
                }
            }

            // 鈹€鈹€ 搴曢儴杈撳叆鍖?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            Surface(
                tonalElevation = 2.dp,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(onClick = { imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    ) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "閫夋嫨鍥剧墖")
                    }

                    IconButton(onClick = {
                        filePickerLauncher.launch(arrayOf("*/*"))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Send,  // 鐢?Send 鍥炬爣鏆備唬鏂囦欢鍥炬爣
                            contentDescription = "閫夋嫨鏂囦欢"
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        TextField(
                            value = inputText,
                            onValueChange = viewModel::updateInputText,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("杈撳叆娑堟伅...") },
                            maxLines = 4,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = { viewModel.sendMessage() })
                        )

                        // Slash 琛ュ叏涓嬫媺
                        DropdownMenu(
                            expanded = showCompletions && completions.isNotEmpty(),
                            onDismissRequest = { showCompletions = false }
                        ) {
                            completions.forEach { (name, desc) ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(name, style = MaterialTheme.typography.bodyMedium)
                                            Text(desc, style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    },
                                    onClick = {
                                        val lastSpace = inputText.lastIndexOf(' ')
                                        val prefix = if (lastSpace >= 0) inputText.substring(0, lastSpace + 1) else ""
                                        viewModel.updateInputText(prefix + name + " ")
                                        showCompletions = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (uiState.isStreaming) {
                        // 娴佸紡鍥炲涓細鏄剧ず鍋滄鎸夐挳
                        IconButton(onClick = { viewModel.abortCurrent() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "鍋滄鐢熸垚",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    } else {
                        IconButton(
                            onClick = viewModel::sendMessage,
                            enabled = inputText.isNotBlank() && !uiState.isSending
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "鍙戦€?
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 鎵撳瓧鎬佹寚绀哄櫒 鈥?涓変釜璺冲姩鐨勭偣
 */
@Composable
private fun TypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 4.dp,
                bottomEnd = 16.dp
            ),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shadowElevation = 1.dp
        ) {
            Text(
                text = "鈼?鈼?鈼?,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
