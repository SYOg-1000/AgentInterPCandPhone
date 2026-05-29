# AgentInter Android 瀹㈡埛绔?鈥?鏋舵瀯璇存槑

> 鏈€鍚庢洿鏂帮細2026-05-29 | 瀵瑰簲浠ｇ爜鐗堟湰锛歮aster (19 commits)

---

## 绯荤粺杈圭晫

```
鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?    WebSocket      鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹? Android App  鈹?鈼勨攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈻?鈹? OpenClaw     鈹?鈹? (姝ら」鐩?     鈹?  ws://host:18789 鈹? Gateway      鈹?鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?                   鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹攢鈹€鈹€鈹€鈹€鈹€鈹€鈹?                                          鈹?                                    鈹屸攢鈹€鈹€鈹€鈹€鈹粹攢鈹€鈹€鈹€鈹€鈹€鈹€鈹?                                    鈹? Claude /    鈹?                                    鈹? DeepSeek    鈹?                                    鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?```

---

## 鍒嗗眰鏋舵瀯

```
鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹? UI Layer (Compose)                     鈹?鈹? ChatScreen 路 MessageItem 路 ImageViewer 鈹?鈹? ThinkingBubble 路 SettingsScreen        鈹?鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹? ViewModel Layer                        鈹?鈹? ChatViewModel                          鈹?鈹?  路 sessionReady 路 isStreaming 路 error  鈹?鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹? Core Layer                             鈹?鈹? 鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹?鈹? 鈹?Gateway   鈹?鈹?Session  鈹?鈹?Room   鈹?鈹?鈹? 鈹?Client    鈹?鈹?Manager  鈹?鈹?DB     鈹?鈹?鈹? 鈹?(WS+RPC)  鈹?鈹?         鈹?鈹?       鈹?鈹?鈹? 鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹?鈹? 鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹屸攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?           鈹?鈹? 鈹?Markdown 鈹?鈹?Image     鈹?           鈹?鈹? 鈹?Parser   鈹?鈹?Picker    鈹?           鈹?鈹? 鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?           鈹?鈹溾攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?鈹? Model Layer                            鈹?鈹? RelayProtocol 路 Message 路 Attachment   鈹?鈹斺攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹?```

---

## 鏍稿績绫昏亴璐?
### 閫氫俊灞?
| 绫?| 鑱岃矗 | 绾跨▼妯″瀷 |
|:---|:---|:---|
| `GatewayClient` | WebSocket 杩炴帴绠＄悊銆丠MAC-SHA256 璁よ瘉銆丷PC 璇锋眰/鍝嶅簲銆佷簨浠跺垎鍙戙€佹寚鏁伴€€閬块噸杩?| IO 鍗忕▼ + OkHttp 鍥炶皟绾跨▼ |
| `SessionManager` | 浼氳瘽瑙ｆ瀽锛坙ist鈫抍reate锛夈€佸綋鍓嶄細璇濈姸鎬佺淮鎶?| 璋冪敤鏂瑰崗绋?|
| `GatewayForegroundService` | Android 鍓嶅彴 Service 淇濇椿銆侀€氱煡鏍忓父椹?| 涓荤嚎绋?+ Service 鐢熷懡鍛ㄦ湡 |

### 鏁版嵁灞?
| 绫?| 鑱岃矗 |
|:---|:---|
| `MessageDao` | Room DAO 鈥?娑堟伅/闄勪欢 CRUD銆佹寜 session 鏌ヨ銆佹棫娑堟伅娓呯悊 |
| `AppDatabase` | Room 鏁版嵁搴撳崟渚嬶紙`agentinter.db`锛?|
| `Entities` | `MessageEntity` + `AttachmentEntity` + `MessageWithAttachments` |

### UI 灞?
| 绫?| 鑱岃矗 |
|:---|:---|
| `ChatViewModel` | 杩炴帴鈫掍細璇濃啋鍘嗗彶鈫掓秷鎭敹鍙戔啋娴佸紡鈫掓墦鏂啋Room 鍚屾 鍏ㄦ祦绋嬬紪鎺?|
| `ChatScreen` | 娑堟伅鍒楄〃 + 杈撳叆鍖?+ Slash 琛ュ叏 + 鍥剧墖/鏂囦欢閫夋嫨 |
| `MessageItem` | 鍗曟潯娑堟伅姘旀场 鈥?绾枃鏈?/ Markdown / 鍥剧墖缂╃暐鍥?|
| `ImageViewer` | 鍏ㄥ睆鍥剧墖棰勮 鈥?鍙屾寚缂╂斁 + 鎷栨嫿 |
| `ThinkingBubble` | 鍙姌鍙犳€濊€冨唴瀹?鈥?鈻?鈻?toggle |
| `SettingsScreen` | 璁剧疆椤?鈥?Gateway 淇℃伅 + 涓婚锛堥鏋讹級 |

### 娓叉煋灞?
| 绫?| 鑱岃矗 |
|:---|:---|
| `MarkdownParser` | 鍧楃骇瑙ｆ瀽锛? 绉?block锛?+ 琛屽唴瑙ｆ瀽锛? 绉?inline锛?|
| `MarkdownRenderer` | Block 鈫?Compose UI |
| `CodeHighlighter` | 8 璇█鍏抽敭璇嶇潃鑹?鈫?`AnnotatedString` |

### 宸ュ叿灞?
| 绫?| 鑱岃矗 |
|:---|:---|
| `ImagePicker` | URI 鈫?閲囨牱缂╂斁 鈫?EXIF 鏃嬭浆 鈫?JPEG 鍘嬬缉 鈫?缂撳瓨 |
| `SlashCompleter` | Slash 鍛戒护鍓嶇紑鍖归厤锛? 鍐呯疆鍛戒护锛?|
| `NotificationHelper` | 閫氱煡娓犻亾 + 杩炴帴閫氱煡 + 娑堟伅閫氱煡 |

---

## 鏁版嵁娴?
### 鍙戦€佹秷鎭?
```
User types 鈫?ChatScreen.onSend()
  鈫?ChatViewModel.sendMessage()
    鈫?Message(id, User, text)  绔嬪嵆鏄剧ず锛堜箰瑙?UI锛?    鈫?saveToRoom()             寮傛鍐?SQLite
    鈫?GatewayClient.sendMessage()
      鈫?WebSocket send RPC request
    鈫?Gateway RPC response {ok, runId}
  鈫?ChatViewModel updates currentRunId

Gateway push: chat event
  鈫?GatewayClient.events (SharedFlow)
  鈫?ChatViewModel.handleGatewayEvent()
    鈫?streaming: append delta to message
    鈫?completed: finalize + saveToRoom()
    鈫?aborted: mark interrupted
  鈫?ChatUiState.messages updated
  鈫?ChatScreen recomposes
```

### 鍚姩娴佺▼

```
ChatViewModel.init
  鈫?loadFromRoom()            绔嬪嵆鏄剧ず缂撳瓨锛堝揩閫熼灞忥級
  鈫?GatewayClient.connect()   WebSocket 杩炴帴
  鈫?wait for Connected        鏈€澶?10s
  鈫?SessionManager.resolveSession()
    鈫?sessions.list           鏌ュ凡鏈変細璇?    鈫?sessions.create("main") 鏃犲垯鍒涘缓
  鈫?loadHistory()             鎷夊彇 50 鏉℃渶鏂版秷鎭?    鈫?鍚堝苟鍒?messages锛堝幓閲嶏級
  鈫?handleGatewayEvent()      寮€濮嬬洃鍚疄鏃舵帹閫?```

### 閲嶈繛娴佺▼

```
WebSocket onFailure/onClosed
  鈫?GatewayClient.reconnect()
    鈫?1s 鈫?2s 鈫?4s 鈫?... 30s 閫€閬匡紝鏈€澶?10 娆?    鈫?姣忔 connect() 鈫?wait for Connected (5s timeout)
  鈫?ChatViewModel 鐩戝惉 connectionState
    鈫?Reconnecting 鈫?Connected 鏃惰嚜鍔?resolveSession()
```

---

## 鍏抽敭璁捐鍐崇瓥

| 鍐崇瓥 | 鍘熷洜 |
|:---|:---|
| 鑷畾涔?Markdown 瑙ｆ瀽鍣紙涓嶇敤搴擄級 | 鍙帶鎬э細浠ｇ爜楂樹寒銆佽〃鏍笺€佸祵濂楀紩鐢ㄥ彲绮剧‘鎺у埗锛屾棤澶栭儴渚濊禆椋庨櫓 |
| Room 鍙屽悜鍚屾锛堝厛鏈湴鍚庤繙绔級 | 棣栧睆闆跺欢杩?+ 绂荤嚎鍙敤 + 杩滅鏁版嵁鑷姩鍚堝苟 |
| `sendRequest` 瓒呮椂 鈫?finally 娓呯悊 | 闃叉 `_pendingRequests` 鍐呭瓨娉勬紡 |
| `disconnect()` 鐘舵€佸厛浜?close | 闃叉 `onClosed` 鍥炶皟绔炴€佽Е鍙?reconnect |
| ViewModel 鏋勯€犲嚱鏁版敞鍏ワ紙闈?`by viewModels()`锛?| Phase 0-11 MVP 绠€鍖栥€傜敓浜ч渶鏀?ViewModelFactory |
| Token 纭紪鐮?| Phase 0-11 MVP銆傜敓浜ч渶 DataStore/QR 鐮佹壂鎻?|
| `[Image]` / `[File]` 鏂囨湰鏍囪 | Phase 6/9 绠€鍖栥€傜敓浜ч渶 base64 鍒嗗潡鎴栧厛涓婁紶鍐嶅紩鐢?URL |

---

## 渚濊禆

| 搴?| 鐢ㄩ€?|
|:---|:---|
| Compose BOM 2024 | UI 妗嗘灦 |
| OkHttp 4.x | WebSocket 瀹㈡埛绔?|
| kotlinx.serialization | JSON 搴忓垪鍖?|
| Room 2.6.1 | SQLite ORM |
| Coil Compose | 鍥剧墖鍔犺浇 |
| ExifInterface 1.3.7 | 鍥剧墖 EXIF 鏂瑰悜 |
| DataStore Preferences | 閰嶇疆鎸佷箙鍖栵紙寰呮帴鍏ワ級 |

---

## 鏂囦欢娓呭崟锛?8 涓?Kotlin 鏂囦欢锛?
```
chat/
  ChatScreen.kt           鈥?涓荤晫闈?+ 杈撳叆鍖?+ Slash 琛ュ叏
  ChatViewModel.kt        鈥?鍏ㄦ祦绋嬬紪鎺?(ViewModels)
  MessageItem.kt          鈥?娑堟伅姘旀场 + 鍥剧墖缂╃暐鍥?  MarkdownParser.kt       鈥?鍧楃骇 + 琛屽唴瑙ｆ瀽鍣?  MarkdownRenderer.kt     鈥?Block 鈫?Compose UI
  CodeHighlighter.kt      鈥?8 璇█鍏抽敭璇嶇潃鑹?  ImageViewer.kt          鈥?鍏ㄥ睆鍥剧墖棰勮
  ImagePicker.kt          鈥?鍥剧墖鍘嬬缉/鏃嬭浆/缂撳瓨
  ThinkingBubble.kt       鈥?鍙姌鍙犳€濊€冨唴瀹?  SlashCompleter.kt       鈥?Slash 鍛戒护琛ュ叏

core/
  config/AppConfig.kt     鈥?閰嶇疆鏁版嵁绫?  model/
    RelayProtocol.kt      鈥?WebSocket 鍗忚妯″瀷
    Message.kt            鈥?娑堟伅/闄勪欢妯″瀷
  network/
    GatewayClient.kt      鈥?WebSocket + RPC 瀹㈡埛绔?    SessionManager.kt     鈥?浼氳瘽绠＄悊
    GatewayForegroundService.kt 鈥?鍓嶅彴 Service
  notification/
    NotificationHelper.kt 鈥?閫氱煡宸ュ叿
  storage/
    Entities.kt           鈥?Room 瀹炰綋
    MessageDao.kt         鈥?Room DAO
    AppDatabase.kt        鈥?Room 鏁版嵁搴撳崟渚?
settings/
  SettingsScreen.kt       鈥?璁剧疆椤?
ui/theme/
  Color.kt                鈥?棰滆壊甯搁噺
  Theme.kt                鈥?Material3 涓婚
  Type.kt                 鈥?瀛椾綋鎺掔増

MainActivity.kt           鈥?鍏ュ彛 Activity
```
