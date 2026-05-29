# 椤圭洰瀹炴柦璁″垝

> 鐗堟湰锛歷1.0 | 鐢熸垚鏃ユ湡锛?026-07-02 | 棰勮宸ユ湡锛?0-14 澶?
---

## 鍗忓悓妯″紡

鏈」鐩噰鐢?**Reasonix + CC 鍙?AI 鍥炲悎鍒跺崗浣?*锛?
```
Reasonix锛堜富鑴戯級            CC锛堟墽琛屾墜锛?    鈹?                         鈹?    鈹溾攢 璋冪爺/瑙勫垝                鈹?    鈹溾攢 鍐?spec/task.md  鈹€鈹€鈹€鈹€鈹€鈹€鈫掆攤
    鈹?                   鈫愨攢鈹€鈹€鈹€鈹€鈹€鈹溾攢 鎵ц锛堜唬鐮?鏋勫缓锛?    鈹溾攢 瀹℃煡 result.md           鈹?    鈹溾攢 璋冩暣涓嬩竴杞?spec          鈹?    鈹?                         鈹?```

| 瑙掕壊 | 鑱岃矗 | 宸ュ叿 |
|:---|:---|:---|
| **Reasonix** | 鏋舵瀯鍐崇瓥銆佸崗璁皟鐮斻€佷换鍔℃媶瑙ｃ€佷唬鐮佸鏌ャ€佹枃妗ｇ淮鎶?| 璇绘枃浠躲€佹悳绱€佽皟鐮斻€佽蹇?|
| **CC** | 浠ｇ爜瀹炵幇銆丟radle 鏋勫缓銆丄PK 璋冭瘯銆佹祴璇曡繍琛?| Edit/Bash/Read锛堝厑璁革級 |

### CC 璋冪敤瑙勮寖

```bash
# 鏂颁换鍔★紙鏂?session锛?claude -n "task-name" -p "瀹屾暣 spec" --output-format json --dangerously-skip-permissions

# 缁х画涓婁竴浠诲姟
claude -c -p "缁啓 spec" --output-format json --dangerously-skip-permissions

# 绮剧‘鎭㈠
claude -r "<session-id>" -p "spec" --output-format json --dangerously-skip-permissions
```

**浠诲姟绮掑害**锛氭瘡涓?CC 浠诲姟鎺у埗鍦?3-5 鍒嗛挓鍐呭畬鎴愩€傚ぇ浠诲姟鎷嗘垚瀛愪换鍔°€?
### 宸ヤ綔鐩綍绾﹀畾

| 璺緞 | 鐢ㄩ€?|
|:---|:---|
| `D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\android\` | Android 椤圭洰鏍?|
| `D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\docs\` | 璁捐鏂囨。 |
| `D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\tasks\` | CC 浠诲姟鐩綍锛坱ask.md / result.md锛?|

---

## 鏃堕棿琛ㄦ€昏

```
Day 1  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  鐜楠岃瘉 + 椤圭洰楠ㄦ灦
Day 2  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  鏍稿績閫氫俊灞?Day 3  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  P0: 鏂囧瓧鏀跺彂
Day 4  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  P0: Markdown 娓叉煋
Day 5  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  P0: 娑堟伅鍒楄〃 + 鏀跺熬
Day 6  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  P1: 鍥剧墖鍙戦€?Day 7  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  P1: 鍥剧墖棰勮 + 鎸佷箙鍖?Day 8  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  楂樼骇: 娴佸紡鎬濊€?+ 鎵撴柇
Day 9  鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  楂樼骇: Slash琛ュ叏 + 鏂囦欢浼犺緭
Day 10 鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  楂樼骇: 涓诲姩鎺ㄩ€?+ 鍓嶅彴鏈嶅姟
Day 11 鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  鎵撶（: 閲嶈繛/閿欒澶勭悊/鏆楄壊妯″紡
Day 12 鈻堚枅鈻堚枅鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒鈻戔枒  娴嬭瘯 + 鏀跺熬
```

---

## Phase 0锛氱幆澧冮獙璇侊紙Day 1 涓婂崍 路 2h锛?
### 鐩爣
纭鎵€鏈夊伐鍏烽摼鍙敤锛屽垵濮嬪寲 Git 浠撳簱銆?
### 鍏蜂綋浠诲姟

| # | 浠诲姟 | 鎵ц鑰?| 棰勮 |
|:---|:---|:---|:---|
| 0.1 | 楠岃瘉 OpenClaw Gateway 杩愯鐘舵€侊紙`openclaw gateway --port 18789`锛?| Reasonix | 10m |
| 0.2 | 楠岃瘉 Gateway WebSocket 鍙繛鎺ワ紙`websocat` 鎴栬剼鏈祴璇?`chat.send`锛?| Reasonix | 20m |
| 0.3 | 楠岃瘉 Android Studio / Gradle / JDK 鐜 | CC | 15m |
| 0.4 | `git init` + `.gitignore` + 鍒濆 commit | Reasonix | 10m |
| 0.5 | 鍒涘缓 `tasks/` 鐩綍锛屽缓绔?CC 浠诲姟妯℃澘 | Reasonix | 10m |

### 浜у嚭
- 鉁?Gateway 鍙甯告敹鍙戞秷鎭?- 鉁?Git 浠撳簱灏辩华
- 鉁?宸ュ叿閾惧叏閮ㄥ彲鐢?
---

## Phase 1锛氶」鐩鏋讹紙Day 1 涓嬪崍 路 3h锛?
### 鐩爣
鐢?Android Studio 妯℃澘鍒濆鍖?Compose 椤圭洰锛屽缓绔嬪寘缁撴瀯锛岄厤缃緷璧栥€?
### 浠诲姟鎷嗗垎

#### Task 1.1锛氬垵濮嬪寲 Gradle 椤圭洰锛圕C 路 20m锛?
```
spec: 鍒涘缓鏍囧噯 Android Compose 椤圭洰
- 椤圭洰鍚嶏細AgentInterPCandPhone
- 鍖呭悕锛歝om.agentinter.pcphone
- minSdk: 26, targetSdk: 34
- 渚濊禆锛欳ompose BOM, Material3, OkHttp, DataStore
- 鍦?android/ 鐩綍涓嬪垵濮嬪寲
```

#### Task 1.2锛氶厤缃?build.gradle 渚濊禆锛圕C 路 15m锛?
```
spec: 鍦?app/build.gradle.kts 涓坊鍔狅細
- OkHttp (WebSocket 鏀寔)
- Kotlinx Serialization (JSON)
- DataStore Preferences
- Room (P1 闃舵锛屽厛鍔犱緷璧栦笉瀹炵幇)
- Coil (鍥剧墖鍔犺浇锛孭1 闃舵)
- 鐗堟湰鐩綍 (libs.versions.toml)
```

#### Task 1.3锛氬缓绔嬪寘缁撴瀯 + Compose 涓婚锛圕C 路 20m锛?
```
spec: 鎸夋灦鏋勬枃妗ｅ垱寤哄寘缁撴瀯锛?- chat/ (ChatScreen.kt 绌哄３)
- settings/ (SettingsScreen.kt 绌哄３)
- core/network/ (RelayClient.kt 绌哄３)
- core/model/ (Message.kt, RelayProtocol.kt)
- core/config/ (AppConfig.kt, ConfigProvider.kt)
- ui/theme/ (Theme.kt, Color.kt, Type.kt)
- MainActivity.kt (鍏ュ彛锛屽鑸埌 ChatScreen)
```

#### Task 1.4锛歍heme + Dark Mode锛圕C 路 15m锛?
```
spec: 瀹炵幇 Material3 涓婚锛屾敮鎸佹繁娴呰壊鍒囨崲
- Theme.kt: AgentInterTheme composable
- Color.kt: 鍝佺墝鑹插畾涔?- Type.kt: 瀛椾綋鎺掔増
- 璺熼殢绯荤粺鏆楄壊妯″紡 (isSystemInDarkTheme)
```

### 浜у嚭
- 鉁?椤圭洰鍙湪 Android Studio 涓墦寮€骞剁紪璇?- 鉁?鍖呯粨鏋勫氨缁紝鍚勬ā鍧楁湁绌哄３鏂囦欢
- 鉁?Material3 涓婚锛堝惈鏆楄壊妯″紡锛夊彲鐢?
### Reasonix 瀹℃煡娓呭崟
- [ ] 鍖呭悕鏄惁姝ｇ‘
- [ ] 渚濊禆鐗堟湰鏄惁鍏煎
- [ ] 缂栬瘧鏄惁閫氳繃锛坄./gradlew assembleDebug`锛?
---

## Phase 2锛氭牳蹇冮€氫俊灞傦紙Day 2 路 4h锛?
### 鐩爣
瀹炵幇 WebSocket 杩炴帴銆丟ateway 璁よ瘉銆佷細璇濈鐞嗐€傝繖鏄暣涓?App 鐨勫熀纭€璁炬柦銆?
### 浠诲姟鎷嗗垎

#### Task 2.1锛氭暟鎹ā鍨嬪畾涔夛紙CC 路 15m锛?
```
spec: 瀹炵幇 core/model/ 涓嬬殑鏁版嵁绫伙細
- Message.kt: id, role (user/assistant/system), content, timestamp, isReasoning, attachments
- RelayProtocol.kt: Gateway WS 甯ф牸寮?  - GatewayRequest(type, id, method, params)
  - GatewayResponse(type, id, ok, payload, error)
  - GatewayEvent(type, event, payload)
  - ConnectParams, ChatSendParams, ChatHistoryParams
- 浣跨敤 kotlinx.serialization
```

#### Task 2.2锛歐ebSocket 瀹㈡埛绔紙CC 路 30m锛?
```
spec: 瀹炵幇 core/network/GatewayClient.kt
- 鍩轰簬 OkHttp WebSocket
- connect(host, port, token): 寤虹珛杩炴帴 + 鍙戦€?connect 甯?- disconnect(): 鍏抽棴杩炴帴
- sendRequest(method, params): 鍙戦€佽姹傚抚锛岃繑鍥炲彲鎸傝捣绛夊緟鍝嶅簲
- 浜嬩欢鍥炶皟: onEvent, onError, onClose
- 鑷姩閲嶈繛 (鎸囨暟閫€閬? 1s, 2s, 4s, 8s, max 30s)
- 蹇冭烦淇濇椿 (30s ping/pong)
- 杩炴帴鐘舵€? Disconnected, Connecting, Connected, Reconnecting
```

#### Task 2.3锛欸ateway 璁よ瘉 + 浼氳瘽鍒濆鍖栵紙CC 路 25m锛?
```
spec: 瀹炵幇 core/network/SessionManager.kt
- authenticate(client: GatewayClient): 鍙戦€?connect 甯у惈 auth token
- resolveSession(): 
  1. 璋?sessions.list 鏌ユ槸鍚︽湁 "main"
  2. 鏃犲垯 sessions.create("main")
  3. 杩斿洖 sessionKey + sessionId
- getHistory(sessionKey): 璋?chat.history 鑾峰彇鍘嗗彶娑堟伅
- 鐘舵€佹寔涔呭寲: sessionKey 瀛?DataStore
```

#### Task 2.4锛氶泦鎴愭祴璇曪紙Reasonix 路 30m锛?
```
鎵嬪姩娴嬭瘯:
1. 鍚姩 OpenClaw Gateway
2. 杩愯 App 鈫?妫€鏌?WebSocket 杩炴帴鐘舵€?3. 楠岃瘉璁よ瘉鎴愬姛
4. 楠岃瘉浼氳瘽鍒涘缓/鎭㈠
```

### 浜у嚭
- 鉁?`GatewayClient` 鍙缓绔?WS 杩炴帴骞惰璇?- 鉁?`SessionManager` 鍙垱寤?鎭㈠浼氳瘽
- 鉁?杩炴帴鐘舵€佹満姝ｅ父宸ヤ綔

### Reasonix 瀹℃煡娓呭崟
- [ ] WS 甯ф牸寮忔槸鍚︾鍚?Gateway protocol
- [ ] 閲嶈繛閫昏緫鏄惁姝ｇ‘
- [ ] 閿欒澶勭悊鏄惁瀹屽杽

---

## Phase 3锛歅0 鈥?鏂囧瓧鏀跺彂锛圖ay 3 路 4h锛?
### 鐩爣
瀹炵幇鏈€鍩烘湰鐨勬秷鎭彂閫佸拰鎺ユ敹锛岀敤鎴峰彲鎵撳瓧鍙戦€佺粰 Reasonix 骞剁湅鍒板洖澶嶃€?
### 浠诲姟鎷嗗垎

#### Task 3.1锛欳hatViewModel 鍩虹锛圕C 路 30m锛?
```
spec: 瀹炵幇 chat/ChatViewModel.kt
- 鎸佹湁 GatewayClient + SessionManager
- uiState: ChatUiState(messages, isLoading, error, connectionStatus)
- sendMessage(text: String): 璋?chat.send
- loadHistory(): 璋?chat.history 鍔犺浇鏈€杩戞秷鎭?- 鐩戝惉 WebSocket chat 浜嬩欢 鈫?鏇存柊 messages 鍒楄〃
- 娑堟伅鍘婚噸锛堟寜 runId + 鍐呭锛?```

#### Task 3.2锛欳hatScreen UI锛圕C 路 40m锛?
```
spec: 瀹炵幇 chat/ChatScreen.kt
- 椤堕儴鏍忥細杩炴帴鐘舵€佹寚绀哄櫒锛堢豢鐐?绾㈢偣锛? 浼氳瘽鍚?- 娑堟伅鍒楄〃锛歀azyColumn锛岃嚜鍔ㄦ粴鍔ㄥ埌搴曢儴
- 杈撳叆妗嗭細TextField + 鍙戦€佹寜閽紙绠ご鍥炬爣锛?- 鍙戦€侀€昏緫锛欵nter 鍙戦€侊紝Shift+Enter 鎹㈣
- 绌虹姸鎬侊細棣栨浣跨敤鏄剧ず寮曞鏂囧瓧
- 鍔犺浇鐘舵€侊細绛夊緟鍥炲鏃舵樉绀?typing 鍔ㄧ敾
```

#### Task 3.3锛歁essageItem 缁勪欢锛圕C 路 25m锛?
```
spec: 瀹炵幇 chat/MessageItem.kt
- 鐢ㄦ埛娑堟伅锛氬彸瀵归綈锛岃摑鑹叉皵娉?- Reasonix 娑堟伅锛氬乏瀵归綈锛岀伆鑹叉皵娉★紝鏀寔澶氭钀?- 鏃堕棿鎴筹紙鐩稿鏃堕棿锛氬垰鍒?3鍒嗛挓鍓?鏄ㄥぉ 14:30锛?- 闀挎寜澶嶅埗鏂囨湰
```

#### Task 3.4锛氱鍒扮娴嬭瘯锛圧easonix 路 30m锛?
```
鎵嬪姩娴嬭瘯:
1. 鍚姩 App 鈫?杩炴帴 Gateway
2. 鍙戦€?"Hello" 鈫?绛夊緟 Reasonix 鍥炲
3. 楠岃瘉娑堟伅鍒楄〃鏇存柊
4. 鍏抽棴 App 鈫?閲嶆柊鎵撳紑 鈫?楠岃瘉鍘嗗彶鍔犺浇
```

### 浜у嚭
- 鉁?鍙彂閫佹枃瀛楁秷鎭苟鐪嬪埌鍥炲
- 鉁?娑堟伅鍒楄〃姝ｇ‘鏄剧ず
- 鉁?鏃堕棿鎴冲拰姘旀场鏍峰紡姝ｅ父

---

## Phase 4锛歅0 鈥?Markdown 娓叉煋锛圖ay 4 路 3h锛?
### 鐩爣
Reasonix 鐨勫洖澶嶏紙鍚唬鐮佸潡銆佽〃鏍笺€佸垪琛級浠ユ纭牸寮忔覆鏌撱€?
### 浠诲姟鎷嗗垎

#### Task 4.1锛歁arkdown 瑙ｆ瀽鍣ㄩ泦鎴愶紙CC 路 30m锛?
```
spec: 瀹炵幇 chat/MarkdownRenderer.kt
- 浣跨敤 markdown 瑙ｆ瀽搴擄紙濡?com.halilibo.compose-richtext 鎴栬嚜瀹氫箟锛?- 鏀寔鐨勮娉曪細
  - 浠ｇ爜鍧?(```) + 璇硶楂樹寒锛堝叧閿瘝鐫€鑹诧級
  - 琛屽唴浠ｇ爜 (`) 鐏拌壊鑳屾櫙
  - 鏍囬 (# ## ###)
  - 鍔犵矖/鏂滀綋
  - 鏃犲簭/鏈夊簭鍒楄〃
  - 琛ㄦ牸锛堢畝鍗?GFM 琛ㄦ牸锛?  - 閾炬帴锛堝彲鐐瑰嚮锛?  - 寮曠敤鍧?(>)
- 浠ｇ爜鍧楀彸涓婅 "澶嶅埗" 鎸夐挳
```

#### Task 4.2锛歁arkdown 闆嗘垚鍒?MessageItem锛圕C 路 20m锛?
```
spec: 淇敼 MessageItem.kt
- 妫€娴嬫秷鎭槸鍚﹀惈 Markdown 鏍囪
- 鍚唬鐮佸潡 鈫?浣跨敤 MarkdownRenderer
- 绾枃鏈?鈫?浣跨敤鍘?Text 娓叉煋
- 浠ｇ爜鍧楁í鍚戞粴鍔紙涓嶆崲琛岋級
```

#### Task 4.3锛氫唬鐮侀珮浜粏鍖栵紙CC 路 20m锛?
```
spec: 涓哄父瑙佽瑷€娣诲姞鍏抽敭璇嶇潃鑹?- Kotlin, Java, Python, JavaScript, TypeScript
- JSON, YAML, XML
- SQL, Shell, Dockerfile
- 姣忕璇█瀹氫箟涓€缁勫叧閿瘝 + 娉ㄩ噴瑙勫垯
- 鏈瘑鍒瑷€ 鈫?绛夊瀛椾綋鏃犻珮浜?```

### 浜у嚭
- 鉁?浠ｇ爜鍧楄娉曢珮浜甯?- 鉁?琛ㄦ牸銆佸垪琛ㄧ瓑娓叉煋姝ｇ‘
- 鉁?澶嶅埗鎸夐挳鍙敤

---

## Phase 5锛歅0 鈥?娑堟伅鍒楄〃鏀跺熬锛圖ay 5 路 2h锛?
### 浠诲姟鎷嗗垎

#### Task 5.1锛氬垎椤靛姞杞斤紙CC 路 25m锛?
```
spec: 娑堟伅鍒楄〃鍒嗛〉
- 鍒濆鍔犺浇鏈€杩?50 鏉?- 婊氬姩鍒伴《閮?鈫?"鍔犺浇鏇村" 鎸夐挳
- chat.history 鐨勫垎椤靛弬鏁?```

#### Task 5.2锛氶敊璇鐞?+ 绌虹姸鎬侊紙CC 路 20m锛?
```
spec:
- 缃戠粶閿欒 鈫?Snackbar 鎻愮ず + 閲嶈瘯鎸夐挳
- Gateway 涓嶅彲杈?鈫?鍏ㄥ睆閿欒椤甸潰
- 浼氳瘽涓㈠け 鈫?鑷姩閲嶅缓
- 棣栨浣跨敤 鈫?寮曞椤甸潰
```

### 浜у嚭
- 鉁?P0 瀹屾暣闂幆锛氬彂鏂囧瓧 鈫?鐪?Markdown 鈫?缈诲巻鍙?
---

## Phase 6锛歅1 鈥?鍥剧墖鍔熻兘锛圖ay 6-7 路 6h锛?
### Day 6锛氬浘鐗囧彂閫?+ 涓婁紶

#### Task 6.1锛氬浘鐗囬€夋嫨鍣紙CC 路 25m锛?
```
spec: 瀹炵幇 ImagePicker 宸ュ叿绫?- 鎷嶇収锛圓ctivityResultContracts.TakePicture锛?- 浠庣浉鍐岄€夋嫨锛圓ctivityResultContracts.PickVisualMedia锛?- 鍘嬬缉锛氭渶澶ц竟闀?2048px锛孞PEG quality 80%
- 杞负 base64 鎴栦繚瀛樹复鏃舵枃浠?```

#### Task 6.2锛氬浘鐗囦笂浼犲埌 chat.send锛圕C 路 30m锛?
```
spec: 淇敼 ChatViewModel.sendMessage()
- 鏀寔闄勪欢鍙傛暟 List<Attachment>
- 鍥剧墖 鈫?璇诲彇涓?bytes 鈫?浣滀负 chat.send attachment 涓婁紶
- 鍙戦€佸墠鏄剧ず缂╃暐鍥鹃瑙?- 涓婁紶杩涘害鎸囩ず
```

#### Task 6.3锛氬浘鐗囨秷鎭?UI锛圕C 路 20m锛?
```
spec: 淇敼 MessageItem.kt
- 鍥剧墖娑堟伅锛氭樉绀虹缉鐣ュ浘锛堝渾瑙掞紝max 200dp 楂橈級
- 鍙戦€佷腑锛氬崐閫忔槑 + 杩涘害鍦?- 鍙戦€佸け璐ワ細绾㈣壊杈规 + 閲嶈瘯鎸夐挳
```

### Day 7锛氬浘鐗囬瑙?+ Room 鎸佷箙鍖?
#### Task 7.1锛氬浘鐗囬瑙堬紙CC 路 25m锛?
```
spec: 瀹炵幇 ImageViewer composable
- 鐐瑰嚮缂╃暐鍥?鈫?鍏ㄥ睆棰勮锛堥粦鑹茶儗鏅級
- 鍙屾寚缂╂斁锛圥inchToZoom锛?- 宸﹀彸婊戝姩鍒囨崲鍥剧墖
- 淇濆瓨鍒扮浉鍐屾寜閽?- 浣跨敤 Coil 鍔犺浇 Gateway 鎵樼 URL
```

#### Task 7.2锛歊oom 鏁版嵁搴擄紙CC 路 35m锛?
```
spec: 瀹炵幇 core/storage/ 涓嬬殑 Room
- MessageEntity: id, sessionKey, role, content, timestamp, isReasoning
- AttachmentEntity: id, messageId, type, localPath, remoteUrl
- MessageDao: insert, getBySession, deleteOld
- AppDatabase: Room.databaseBuilder
- 姣忔鏀跺埌鏂版秷鎭?鈫?鑷姩鍐欏叆 Room
- App 鍚姩 鈫?浠?Room 鍔犺浇鍘嗗彶锛堝啀涓?chat.history 鍚堝苟鍘婚噸锛?```

### 浜у嚭
- 鉁?鎷嶇収/閫夊浘 鈫?鍘嬬缉 鈫?涓婁紶 鈫?缂╃暐鍥炬樉绀?- 鉁?鐐瑰嚮缂╃暐鍥?鈫?鍏ㄥ睆缂╂斁
- 鉁?閲嶅惎 App 鍘嗗彶涓嶄涪

---

## Phase 7锛氶珮绾у姛鑳斤紙Day 8-10 路 10h锛?
### Day 8锛氭祦寮忔€濊€?+ 鎵撴柇

#### Task 8.1锛氭祦寮忚緭鍑猴紙CC 路 35m锛?
```
spec: 淇敼 GatewayClient 浜嬩欢澶勭悊
- chat 浜嬩欢 delta 澧為噺杩藉姞鍒板綋鍓?assistant 娑堟伅
- 浣跨敤 Compose mutableStateListOf 瀹炴椂鏇存柊 UI
- 娴佸紡娓叉煋鏃?typing 鎸囩ず鍣?- 娑堟伅缁撴潫鏍囪 鈫?鍐欏叆 Room + 鍏抽棴 typing
```

#### Task 8.2锛氭€濊€冩姌鍙狅紙CC 路 30m锛?
```
spec: 瀹炵幇 ThinkingBubble composable
- 妫€娴?isReasoning: true 鐨?chat 浜嬩欢
- 榛樿鎶樺彔涓?"Thinking..." 鐏拌壊鏉?- 鐐瑰嚮灞曞紑 鈫?鏄剧ず鎬濊€冨唴瀹癸紙鏂滀綋锛岀◢鏆楋級
- 绫讳技妗岄潰 Ctrl+O 鐨勬晥鏋?- 璁剧疆閲屽彲鍒囨崲锛氭姌鍙?灞曞紑/闅愯棌
```

#### Task 8.3锛氭墦鏂姛鑳斤紙CC 路 15m锛?
```
spec: 瀹炵幇 AbortButton
- 褰?messageState == STREAMING 鏃舵樉绀?"鍋滄鐢熸垚" 鎸夐挳
- 鐐瑰嚮 鈫?璋?chat.abort(sessionKey, runId)
- UI 绔嬪嵆鏄剧ず "(宸蹭腑鏂?" 鏍囪
- 宸茬敓鎴愮殑閮ㄥ垎鍐呭淇濈暀
```

### Day 9锛歋lash 琛ュ叏 + 鏂囦欢浼犺緭

#### Task 8.4锛歋lash 琛ュ叏锛圕C 路 30m锛?
```
spec: 瀹炵幇 SlashCommandCompleter
- 杈撳叆妗嗙洃鍚細褰撳墠璇嶄互 / 寮€澶?鈫?瑙﹀彂琛ュ叏
- 璋?commands.list 鑾峰彇鍛戒护鍒楄〃
- 鏈湴 Trie 绱㈠紩 + 妯＄硦鍖归厤
- 寮瑰嚭涓嬫媺鑿滃崟锛堟渶澶?5 椤癸級
- Tab/鐐瑰嚮 鈫?鑷姩琛ュ叏
- 鍛戒护 + 绠€鐭鏄庢枃瀛?```

#### Task 8.5锛氭枃浠朵笂浼狅紙CC 路 25m锛?
```
spec: 鎵╁睍 chat.send 闄勪欢鏀寔
- 鏂囦欢閫夋嫨鍣紙ActivityResultContracts.OpenDocument锛?- 鏀寔绫诲瀷锛歵xt, pdf, doc, zip, apk 绛?- 鏂囦欢澶у皬闄愬埗锛?0MB
- 涓婁紶杩涘害鏉?- 鍙戦€佸悗鏄剧ず鏂囦欢鍗＄墖锛堟枃浠跺悕 + 澶у皬 + 鍥炬爣锛?```

#### Task 8.6锛氭枃浠舵帴鏀讹紙CC 路 20m锛?
```
spec: 瀹炵幇 FileReceiver
- 妫€娴?chat 浜嬩欢涓殑鏂囦欢/濯掍綋 URL
- 鐐瑰嚮 鈫?璋?artifacts.download
- 涓嬭浇鍒?Downloads 鐩綍
- 閫氱煡鏍忔樉绀轰笅杞借繘搴?- 瀹屾垚鍚?鈫?绯荤粺鏂囦欢绠＄悊鍣ㄦ墦寮€
```

### Day 10锛氫富鍔ㄦ帹閫?+ 鍓嶅彴鏈嶅姟

#### Task 8.7锛氬墠鍙版湇鍔★紙CC 路 30m锛?
```
spec: 瀹炵幇 GatewayForegroundService
- 鍓嶅彴 Service 淇濇寔 WebSocket 闀胯繛鎺?- 閫氱煡鏍忔樉绀?"AgentInter 路 宸茶繛鎺?
- 鍒囨崲鍒板悗鍙版椂 WebSocket 涓嶄腑鏂?- 鏀跺埌鏂版秷鎭?鈫?Android Notification锛圡essagingStyle锛?- 閫氱煡鐐瑰嚮 鈫?鎵撳紑 ChatScreen
- 蹇嵎鍥炲锛氶€氱煡鏍忕洿鎺ヨ緭鍏ユ枃瀛楀洖澶?```

#### Task 8.8锛歸ake 闆嗘垚锛圕C 路 20m锛?
```
spec:
- Gateway 渚ч厤缃?cron job 鎴?webhook
- App 鎺ユ敹 wake 鎺ㄩ€?鈫?閫氱煡鏍忔彁閱?- 鍦烘櫙锛歊easonix 瀹氭椂鎻愰啋 / 闀夸换鍔″畬鎴愰€氱煡
```

### 浜у嚭
- 鉁?娴佸紡杈撳嚭 + 鎬濊€冩姌鍙?灞曞紑
- 鉁?鍙墦鏂璇?- 鉁?Slash 鍛戒护鑷姩琛ュ叏
- 鉁?鍙屽悜鏂囦欢浼犺緭
- 鉁?鍓嶅彴鏈嶅姟淇濇椿 + 閫氱煡鎺ㄩ€?
---

## Phase 8锛氭墦纾紙Day 11-12 路 6h锛?
### Day 11

#### Task 9.1锛氳繛鎺ュ仴澹€э紙CC 路 25m锛?
```
spec:
- 瀹屾暣鐨勯噸杩炵姸鎬佹満 + UI 鎸囩ず
- 缃戠粶鍒囨崲锛圵iFi 鈫?4G锛夎嚜鍔ㄦ仮澶?- Gateway 閲嶅惎鍚庤嚜鍔ㄩ噸杩?+ 浼氳瘽鎭㈠
- 娑堟伅鍙戦€侀槦鍒楋細绂荤嚎鏃剁紦瀛橈紝鎭㈠鍚庢寜搴忓彂閫?```

#### Task 9.2锛氳缃〉闈紙CC 路 30m锛?
```
spec: 瀹炵幇 SettingsScreen
- Gateway 鍦板潃/绔彛閰嶇疆
- Token 杈撳叆锛堝姞瀵嗗瓨鍌級
- 杩炴帴妯″紡锛氬眬鍩熺綉 / Tailscale
- 鏆楄壊妯″紡鍒囨崲
- 鎬濊€冩樉绀猴細鎶樺彔 / 灞曞紑 / 闅愯棌
- 娑堟伅鍘嗗彶娓呴櫎
- 鍏充簬椤甸潰
```

#### Task 9.3锛氶敊璇鐞嗗叏灞€鍖栵紙CC 路 20m锛?
```
spec:
- 缁熶竴閿欒绫诲瀷锛歂etworkError, AuthError, SessionError, ServerError
- Toast/Snackbar 鏄剧ず鐢ㄦ埛鍙嬪ソ淇℃伅
- 鏃ュ織绯荤粺锛圖ebug 妯″紡锛?```

### Day 12

#### Task 9.4锛氭€ц兘浼樺寲锛圕C 路 25m锛?
```
spec:
- LazyColumn key 浼樺寲锛堥伩鍏嶉噸缁勶級
- 鍥剧墖鍔犺浇缂撳瓨锛圕oil disk cache锛?- WebSocket 娑堟伅鍚堝苟锛坉ebounce锛?- 闀挎秷鎭垪琛ㄨ櫄鎷熷寲
```

#### Task 9.5锛氭祴璇曪紙Reasonix + CC 路 1h锛?
```
spec:
- 绔埌绔祴璇曪細鍏ㄩ儴鍔熻兘鐨勫畬鏁存祦绋?- 杈圭晫娴嬭瘯锛氭柇缃?寮辩綉/Gateway 閲嶅惎/澶ф枃浠?- UI 娴嬭瘯锛氭殫鑹叉ā寮?妯睆/涓嶅悓灞忓箷灏哄
```

### 浜у嚭
- 鉁?App 鍦ㄥ悇绉嶇綉缁滄潯浠朵笅绋冲畾杩愯
- 鉁?璁剧疆椤甸潰瀹屾暣鍙敤
- 鉁?鏃犳槑鏄炬€ц兘闂

---

## CC 浠诲姟鐩綍缁撴瀯

姣忎釜 phase 鐨?CC 浠诲姟鏀惧湪 `tasks/` 涓嬶細

```
tasks/
鈹溾攢鈹€ phase0-env/
鈹?  鈹斺攢鈹€ task-0.2-gateway-test.md
鈹溾攢鈹€ phase1-skeleton/
鈹?  鈹溾攢鈹€ task-1.1-init.md
鈹?  鈹溾攢鈹€ task-1.2-deps.md
鈹?  鈹溾攢鈹€ task-1.3-package.md
鈹?  鈹斺攢鈹€ task-1.4-theme.md
鈹溾攢鈹€ phase2-core/
鈹?  鈹溾攢鈹€ task-2.1-models.md
鈹?  鈹溾攢鈹€ task-2.2-ws-client.md
鈹?  鈹溾攢鈹€ task-2.3-session.md
鈹?  鈹斺攢鈹€ results/
鈹溾攢鈹€ phase3-p0-text/
鈹?  ...
鈹溾攢鈹€ phase4-p0-markdown/
鈹?  ...
鈹溾攢鈹€ phase5-p0-list/
鈹?  ...
鈹溾攢鈹€ phase6-p1-image/
鈹?  ...
鈹溾攢鈹€ phase7-advanced/
鈹?  ...
鈹斺攢鈹€ phase8-polish/
    ...
```

姣忎釜 `task-*.md` 鍖呭惈锛氱洰鏍囥€佷笂涓嬫枃銆佹湡鏈涗骇鍑恒€侀獙鏀舵爣鍑嗐€?
**CC task 缂栧啓閾佸緥**锛堟潵鑷?subagent-discipline 鏁欒锛夛細
- 涓€涓?task 鍙仛涓€浠朵簨锛堝"瀹炵幇 Message.kt 鏁版嵁绫?鑰岄潪"瀹炵幇鎵€鏈夋暟鎹ā鍨?锛?- 棰勮鎵ц鏃堕棿 3-5 鍒嗛挓锛岃秴杩囧氨鎷?- 绂佹鍦?task spec 涓澶氫釜涓嶇浉鍏崇殑瀛愪换鍔?- 濡傛灉 CC 鐨勫洖澶嶈秴杩?200 琛?鈫?task 鑼冨洿澶ぇ

---

## 閲岀▼纰戞鏌ョ偣

| 閲岀▼纰?| 棰勮瀹屾垚 | 楠屾敹鏍囧噯 |
|:---|:---|:---|
| M0: 鐜灏辩华 | Day 1 涓崍 | Gateway 鍙繛鎺ワ紝Gradle 缂栬瘧閫氳繃 |
| M1: 楠ㄦ灦璺戦€?| Day 1 鏅氫笂 | 椤圭洰缂栬瘧锛屼富棰樺睍绀猴紝瀵艰埅姝ｅ父 |
| M2: 閫氫俊鎵撻€?| Day 2 鏅氫笂 | WebSocket 璁よ瘉鎴愬姛锛屼細璇濆垱寤?鎭㈠ |
| M3: P0 鏂囧瓧 | Day 3 鏅氫笂 | 鍙戞枃瀛?鈫?鏀跺洖澶?鈫?鐪嬪巻鍙?|
| M4: P0 Markdown | Day 4 鏅氫笂 | 浠ｇ爜鍧楅珮浜€佽〃鏍笺€佸鍒舵寜閽?|
| M5: P0 瀹屾垚 | Day 5 鏅氫笂 | 涓夊ぇ浠跺叏閮ㄥ彲鐢?|
| M6: P1 鍥剧墖 | Day 7 鏅氫笂 | 鎷嶇収/閫夊浘 鈫?涓婁紶 鈫?棰勮 鈫?鎸佷箙鍖?|
| M7: 娴佸紡+鎵撴柇 | Day 8 鏅氫笂 | 瀹炴椂杈撳嚭 + 鎬濊€冩姌鍙?+ 涓柇 |
| M8: 琛ュ叏+鏂囦欢 | Day 9 鏅氫笂 | Slash 琛ュ叏 + 鍙屽悜鏂囦欢 |
| M9: 鎺ㄩ€?| Day 10 鏅氫笂 | 鍓嶅彴鏈嶅姟 + 閫氱煡 + wake |
| M10: 鎵撶（ | Day 12 鏅氫笂 | 鍏ㄥ姛鑳界ǔ瀹氾紝鍙棩甯镐娇鐢?|

---

---

## 涓婁笅鏂囩鐞嗭細Reasonix + CC 鍙岃建闃蹭涪澶?
### 闂

闀夸换鍔′腑涓や釜 AI 閮藉彲鑳介伃閬囦笂涓嬫枃 compact锛?
| 瀵硅薄 | 鐥囩姸 | 鍚庢灉 |
|:---|:---|:---|
| Reasonix | 瀵硅瘽鍘嗗彶鎴柇 | 蹇樿褰撳墠 Phase銆丆C session_id銆佷笂娆″鏌ョ粨鏋?|
| CC | 浼氳瘽鍘嗗彶鎴柇 | 蹇樿涔嬪墠鐨勪唬鐮佹敼鍔ㄣ€侀」鐩粨鏋勩€佺紪璇戦敊璇巻鍙?|

### Reasonix 鎭㈠鏈哄埗

褰?Reasonix 涓婁笅鏂?compact 鍚庯紝鎴戜細锛?
1. `read_file docs/PROGRESS.md` 鈥?鑾峰彇褰撳墠 Phase銆丆C session銆佹渶杩?checkpoint
2. `recall_memory agentinter-progress project` 鈥?鑾峰彇椤圭洰璁板繂
3. 鎸夐渶 `read_file docs/architecture.md` 鈥?鎭㈠鏋舵瀯鑳屾櫙
4. 缁х画鎵ц

**姣?Phase 瀹屾垚鍚庢洿鏂?*锛歚PROGRESS.md` + `agentinter-progress` memory锛岀‘淇?compact 鍚庡彲鎭㈠銆?
### CC 浼氳瘽绛栫暐锛氫綍鏃?new vs continue

CC 鐨?`-c`锛坈ontinue锛夊埄鐢ㄧ紦瀛橈紝渚垮疁 10 鍊嶃€備絾涓婁笅鏂囨棤闄愬闀挎渶缁堜篃浼?compact銆?
**绛栫暐锛氫竴 Phase 涓€ Session**

```
Phase 1 鈫?CC session "agentinter-p1-skeleton"
  鈹溾攢鈹€ Task 1.1: -c (浠?session 缁х画)
  鈹溾攢鈹€ Task 1.2: -c
  鈹溾攢鈹€ Task 1.3: -c
  鈹斺攢鈹€ Task 1.4: -c

Phase 2 鈫?CC session "agentinter-p2-core"    鈫?鏂?session
  鈹溾攢鈹€ Task 2.1: -c
  鈹溾攢鈹€ Task 2.2: -c
  鈹斺攢鈹€ Task 2.3: -c
```

| 瑙勫垯 | 璇存槑 |
|:---|:---|
| **鏂?Phase = 鏂?Session** | 姣忎釜 Phase 鐙珛鐨?CC 浼氳瘽锛屼笂涓嬫枃骞插噣 |
| **Phase 鍐?= Continue** | 鍚?Phase 鐨勪换鍔＄敤 `-c` 缁啓锛屽埄鐢ㄧ紦瀛?|
| **缂栬瘧澶辫触淇 = Continue** | 灏忎慨灏忚ˉ涓嶆柊寤?session |
| **CC compact 鍚?= 鏂?Session** | 濡傛灉 CC 鐨勪笂涓嬫枃涔熻 compact锛屾柊寤?session 骞堕檮甯﹀綋鍓嶄唬鐮佹憳瑕?|
| **Session ID 璁板綍鍦?PROGRESS.md** | 纭繚 Reasonix 鎭㈠鍚庤兘鎵惧埌姝ｇ‘鐨?CC session |

### CC Session 鍒涘缓妯℃澘

```bash
# Phase N 鐨勭涓€涓?task
claude -n "agentinter-pN-xxx" \
  -p "椤圭洰: AgentInterPCandPhone (D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\android\)
      
      鏋舵瀯涓婁笅鏂? (Phase 鐩稿叧鐨勬灦鏋勬憳瑕?
      
      浠诲姟: (鍏蜂綋 spec)" \
  --output-format json \
  --dangerously-skip-permissions

# Phase N 鐨勫悗缁?task (fix/continue)
claude -c -p "缁х画涓婁釜浠诲姟銆傞棶棰? ..." \
  --output-format json \
  --dangerously-skip-permissions
```

### CC compact 鎭㈠

濡傛灉 CC 浼氳瘽鐨勪笂涓嬫枃琚埅鏂紝鏂板缓 session 鏃舵敞鍏ュ繀瑕佷笂涓嬫枃锛?
```
claude -n "agentinter-pN-xxx-v2" \
  -p "椤圭洰鎭㈠銆備箣鍓嶇殑 session 涓婁笅鏂囦涪澶便€?      
      褰撳墠浠ｇ爜鐘舵€? (鍏抽敭鏂囦欢璺緞 + 绠€瑕佽鏄?
      涓婃閬楃暀闂: (鏈畬鎴愮殑 task)
      鏋舵瀯鏂囨。: 鍙傝€?D:\ADraft\AAA_PROJECT\AgentInterPCandPhone\docs\architecture.md
      
      缁х画鎵ц: (鍘?task spec)" \
  --output-format json \
  --dangerously-skip-permissions
```

### 鐘舵€佹枃浠舵洿鏂拌妭濂?
```
姣忎釜 Phase 瀹屾垚 鈫?  鈹溾攢鈹€ update docs/PROGRESS.md (Phase 鐘舵€?+ CC session + checkpoint)
  鈹溾攢鈹€ update project memory (agentinter-progress)
  鈹斺攢鈹€ [鍙€塢 git commit

姣忎釜 CC task 瀹屾垚 鈫?  鈹溾攢鈹€ 淇濆瓨 task spec 涓?tasks/phaseN/task-N.M.md
  鈹溾攢鈹€ 淇濆瓨 CC result 涓?tasks/phaseN/results/task-N.M-result.md
  鈹斺攢鈹€ 鏇存柊 PROGRESS.md 涓殑 CC session ID
```

### 鎸佷箙鍖栭摼璺?
```
Reasonix 璁板繂          Project Memory
     鈫?                     鈫?docs/PROGRESS.md  鈫愨啋  ~/.reasonix/memory/.../agentinter-progress.md
     鈫?                     鈫?implementation-plan.md    (compact 鏃跺彲鎭㈠)
```

---

## 椋庨櫓涓庡簲瀵?
| 椋庨櫓 | 姒傜巼 | 搴斿 |
|:---|:---|:---|
| Gateway 鍗忚缁嗚妭涓庢枃妗ｄ笉涓€鑷?| 涓?| Day 0 鍏堝仛瀹炶繛娴嬭瘯锛屽彂鐜板樊寮傜珛鍗充慨姝?spec |
| Compose Markdown 娓叉煋鎬ц兘宸?| 浣?| 澶囬€夛細WebView + Markdown CSS |
| OkHttp WebSocket 閲嶈繛閫昏緫澶嶆潅 | 涓?| 鍏堝仛绠€鍗曢噸杩烇紝鍚庣画杩唬瀹屽杽 |
| Room 杩佺Щ锛圥1 鍔犲瓧娈碉級 | 浣?| 浣跨敤 fallbackToDestructiveMigration 鍒濇湡涓嶇籂缁?|
| Android 鍚庡彴闄愬埗鏉€ Service | 涓?| 鍓嶅彴 Service + 鐢垫睜浼樺寲鐧藉悕鍗曞紩瀵?|
