# 鏂板璇濇帴鎵嬫枃妗?
> 鍐欑粰涓嬩竴涓?Reasonix 浼氳瘽鐨勬憳瑕併€傝瀹屾湰鏂囨。鍚庡嵆鎺屾彙褰撳墠鐘舵€侊紝鏃犻渶閲嶈鍏ㄩ儴浠ｇ爜銆?
---

## 涓€鍙ヨ瘽鐘舵€?
**AgentInter Android 瀹㈡埛绔?11 Phase 鍏ㄩ儴瀹屾垚锛?8 涓?Kotlin 鏂囦欢 ~4,500 琛岋紝寰呯紪璇戣繍琛岄獙璇併€?*

---

## 浣犳帴鎵嬪悗闇€瑕佸仛浠€涔?
| 浼樺厛绾?| 浠诲姟 | 澶囨敞 |
|:---|:---|:---|
| P0 | 閰嶇疆缂栬瘧鐜骞剁紪璇?| JDK 17 + Android SDK 34 + `./gradlew assembleDebug` |
| P0 | 淇缂栬瘧閿欒 | 鏃犵紪璇戦獙璇佽繃锛屽ぇ姒傜巼鏈?import 缂哄け/绫诲瀷涓嶅尮閰?|
| P0 | 杩炴帴 Gateway 璺戦€氭枃瀛楁敹鍙?| 淇敼 `MainActivity.kt` 涓?host/port/token |
| P1 | 楠岃瘉 Markdown 娓叉煋 | 鍙戦€佸惈浠ｇ爜鍧楃殑娴嬭瘯娑堟伅 |
| P1 | 楠岃瘉鍥剧墖閫夋嫨鈫掑帇缂┾啋鍙戦€佲啋棰勮 | 闇€瑕佺湡瀹?Android 璁惧 |
| P2 | 楠岃瘉娴佸紡杈撳嚭+鎵撴柇 | 闇€瑕?Gateway 杩愯涓?|
| P2 | 楠岃瘉鍓嶅彴 Service 閫氱煡 | 闇€瑕佺湡鏈猴紙妯℃嫙鍣ㄩ€氱煡鍙楅檺锛?|

---

## 鍏抽敭鏂囦欢绱㈠紩

```
# 缂栬瘧鍏ュ彛
android/build.gradle.kts          鈥?鏍?build锛圞SP 1.9.24-1.0.20锛?android/app/build.gradle.kts      鈥?渚濊禆锛歊oom 2.6.1, Coil, OkHttp, ExifInterface
android/gradle/libs.versions.toml 鈥?鐗堟湰鐩綍

# 鍚姩娴佺▼
MainActivity.kt                   鈥?config 纭紪鐮?鈫?GatewayClient 鈫?SessionManager 鈫?AppDatabase 鈫?ChatViewModel

# 鏍稿績閫昏緫
core/network/GatewayClient.kt     鈥?WebSocket + HMAC 璁よ瘉 + RPC + 浜嬩欢 + 閲嶈繛
core/network/SessionManager.kt    鈥?sessions.list/create
chat/ChatViewModel.kt             鈥?鍏ㄦ祦绋嬬紪鎺掞紙杩炴帴鈫掍細璇濃啋鍘嗗彶鈫掓敹鍙戔啋娴佸紡鈫掓墦鏂啋Room锛?
# UI
chat/ChatScreen.kt                鈥?涓荤晫闈?+ 杈撳叆鍖?+ Slash 琛ュ叏 + 鍥剧墖/鏂囦欢鎸夐挳
chat/MessageItem.kt               鈥?娑堟伅姘旀场 + Markdown 妫€娴?+ 鍥剧墖缂╃暐鍥?chat/MarkdownParser.kt            鈥?鍧楃骇 + 琛屽唴 Markdown 瑙ｆ瀽
chat/MarkdownRenderer.kt          鈥?Block 鈫?Compose
chat/CodeHighlighter.kt           鈥?8 璇█鍏抽敭璇嶇潃鑹?chat/ImageViewer.kt               鈥?鍏ㄥ睆鍥剧墖棰勮
chat/ThinkingBubble.kt            鈥?鍙姌鍙犳€濊€冩皵娉?chat/ImagePicker.kt               鈥?鍥剧墖鍘嬬缉/鏃嬭浆/缂撳瓨
chat/SlashCompleter.kt            鈥?Slash 琛ュ叏

# 鏁版嵁
core/storage/AppDatabase.kt       鈥?Room 鏁版嵁搴撳崟渚?core/storage/Entities.kt          鈥?MessageEntity + AttachmentEntity
core/storage/MessageDao.kt        鈥?DAO

# 骞冲彴
core/network/GatewayForegroundService.kt 鈥?鍓嶅彴 Service
core/notification/NotificationHelper.kt  鈥?閫氱煡宸ュ叿
settings/SettingsScreen.kt        鈥?璁剧疆椤?```

---

## 宸茬煡闂锛堟帴鎵嬪悗蹇呴』澶勭悊锛?
| # | 闂 | 淇寤鸿 |
|:---|:---|:---|
| 1 | Token 纭紪鐮佸湪 `MainActivity.kt` | 鎺ュ叆 DataStore 鎴?BuildConfig |
| 2 | ViewModel 闈炵敓鍛藉懆鏈熸劅鐭ワ紙`new ChatViewModel(...)`锛?| 鏀逛负 `by viewModels()` + Factory |
| 3 | `loadMore()` 鍘婚噸閫昏緫鏁堢巼浣庯紙姣忔閲嶆媺鍏ㄩ噺锛?| 鏀?Gateway 鍗忚鏀寔 `before` 娓告爣锛屾垨鏈湴缁存姢 cursor |
| 4 | 鍥剧墖/鏂囦欢浼犺緭鏄?`[Image]`/`[File]` 鏂囨湰鍗犱綅 | Phase 鍚庣画瀹炵幇鐪熷疄涓婁紶鍗忚 |
| 5 | `SettingsScreen` 鍔熻兘绠€闄?| 鎺ュ叆 DataStore 瀹炵幇鍙紪杈戦厤缃?|
| 6 | 鍓嶅彴 Service 鍐?GatewayClient 鏈敞鍏?| 闇€瑕佺敤 Application 鍗曚緥鎸佹湁 client |

---

## 璁捐鍐崇瓥閫熸煡

| 鍐崇瓥 | 涓轰粈涔?|
|:---|:---|
| 鑷畾涔?Markdown 瑙ｆ瀽鍣紙涓嶇敤绗笁鏂瑰簱锛?| 鍙帶鎬р€斺€斾唬鐮侀珮浜?琛ㄦ牸/宓屽寮曠敤绮剧‘鎺у埗 |
| Room 鍏堟湰鍦板悗杩滅鍚屾 | 棣栧睆闆跺欢杩?+ 绂荤嚎鍙敤 |
| `disconnect()` 鍏堣鐘舵€佸啀 close | 闃?`onClosed` 绔炴€佽Е鍙?reconnect |
| `sendRequest` finally 娓呯悊 pending | 闃茶秴鏃惰姹傚唴瀛樻硠婕?|
| 閲嶈繛鐢?`_connectionState.first { Connected }` | 涓嶇敤鍥哄畾 `delay(3000)` |

---

## 閲嶈鏁欒

**涓嶈鐢?CC subagent 鍒涘缓鏂囦欢銆?* CC 鍦?Phase 6-8 涓ゆ澹扮О鍒涘缓鏂囦欢锛圛magePicker.kt銆両mageViewer.kt锛変絾瀹為檯鏈啓鍏ャ€傚悗缁?6 涓?Phase 鍏ㄩ儴鐢?Reasonix 鐩存帴鎵嬪啓鈥斺€旈浂闂銆侰C 鍙€傚悎鍙璋冪爺銆?
---

## 鏂囨。

| 鏂囦欢 | 鍐呭 |
|:---|:---|
| `docs/ARCHITECTURE.md` | 鍒嗗眰鏋舵瀯鍥俱€佺被鑱岃矗琛ㄣ€佹暟鎹祦銆佽璁″喅绛?|
| `docs/PROGRESS.md` | 鍚?Phase 鐘舵€?+ 鏈€杩?checkpoint |
| `android/README.md` | 椤圭洰姒傝堪銆佸姛鑳芥竻鍗曘€佹瀯寤烘寚鍗?|
| `docs/implementation-plan.md` | 鍘熷瀹炴柦璁″垝锛圖ay 1-6锛?|

---

## 缂栬瘧鍛戒护

```bash
cd D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\android
gradlew assembleDebug
```

鍓嶇疆鏉′欢锛歚JAVA_HOME` 鎸囧悜 JDK 17锛宍ANDROID_HOME` 鎸囧悜 Android SDK銆?