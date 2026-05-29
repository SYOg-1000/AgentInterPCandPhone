# AgentInter 鈥?Android 瀹㈡埛绔?
> 閫氳繃 OpenClaw Gateway 涓?Claude/DeepSeek 瀵硅瘽鐨?Android 鍘熺敓瀹㈡埛绔€?
---

## 鐘舵€?
**鎵€鏈?11 Phase 瀹屾垚锛屽緟缂栬瘧杩愯楠岃瘉銆?*

- Kotlin: 28 鏂囦欢, ~4,500 琛?- 鏈€浣?Android API: 26 (Android 8.0)
- 鐩爣 SDK: 34

---

## 鍔熻兘

| 鍔熻兘 | 鐘舵€?|
|:---|:---|
| 鏂囧瓧鏀跺彂锛堝彂閫?鍘嗗彶/鍒嗛〉锛?| 鉁?|
| Markdown 娓叉煋锛堜唬鐮侀珮浜?8 璇█锛?| 鉁?|
| 娴佸紡杈撳嚭 + 鎬濊€冩姌鍙?+ 鎵撴柇 | 鉁?|
| 鍥剧墖鍙戦€侊紙鎷嶇収/鐩稿唽鈫掑帇缂╋級 | 鉁?|
| 鍥剧墖棰勮锛堝叏灞?缂╂斁锛?| 鉁?|
| 鏂囦欢鍙戦€?| 鉁?|
| Slash 鍛戒护琛ュ叏 | 鉁?|
| Room 鏈湴鎸佷箙鍖?| 鉁?|
| 鍓嶅彴 Service + 閫氱煡鎺ㄩ€?| 鉁?|
| 鏆楄壊妯″紡 | 鉁?|
| 鏂嚎鑷姩閲嶈繛锛堟寚鏁伴€€閬匡級 | 鉁?|

---

## 鍓嶇疆鏉′欢

1. **OpenClaw Gateway** 杩愯鍦ㄥ彲璁块棶鐨勫湴鍧€锛堥粯璁?`127.0.0.1:18789`锛?2. Android Studio Hedgehog+ 鎴栧懡浠よ Gradle
3. JDK 17
4. Android SDK 34

---

## 鏋勫缓

```bash
cd android
./gradlew assembleDebug
```

杈撳嚭 APK: `app/build/outputs/apk/debug/app-debug.apk`

---

## 閰嶇疆

Gateway 杩炴帴鍙傛暟鍦?`MainActivity.kt` 涓‖缂栫爜锛圡VP 闃舵锛夛細

```kotlin
val config = AppConfig(
    gatewayHost = "127.0.0.1",  // Gateway 鍦板潃
    gatewayPort = 18789,        // Gateway 绔彛
    gatewayToken = "..."        // Gateway Token
)
```

楠岃瘉 Gateway 杩為€氭€э細

```bash
openclaw gateway call health
```

---

## 椤圭洰缁撴瀯

```
android/
鈹溾攢鈹€ app/src/main/
鈹?  鈹溾攢鈹€ AndroidManifest.xml
鈹?  鈹斺攢鈹€ java/com/agentinter/pcphone/
鈹?      鈹溾攢鈹€ MainActivity.kt
鈹?      鈹溾攢鈹€ chat/           # UI + ViewModel + 娓叉煋
鈹?      鈹溾攢鈹€ core/
鈹?      鈹?  鈹溾攢鈹€ config/     # 閰嶇疆妯″瀷
鈹?      鈹?  鈹溾攢鈹€ model/      # 鍗忚 + 娑堟伅妯″瀷
鈹?      鈹?  鈹溾攢鈹€ network/    # WebSocket + Session
鈹?      鈹?  鈹溾攢鈹€ notification/ # 閫氱煡宸ュ叿
鈹?      鈹?  鈹斺攢鈹€ storage/    # Room 鏁版嵁搴?鈹?      鈹溾攢鈹€ settings/       # 璁剧疆椤?鈹?      鈹斺攢鈹€ ui/theme/       # Material3 涓婚
鈹溾攢鈹€ build.gradle.kts
鈹斺攢鈹€ gradle/
```

璇﹁ [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md)

---

## 娉ㄦ剰浜嬮」

- Token 纭紪鐮佸湪婧愮爜涓紝**鐢熶骇閮ㄧ讲鍓嶉渶鏀逛负鐜鍙橀噺/DataStore/QR 鐮佹壂鎻?*
- ViewModel 浣跨敤鏋勯€犲嚱鏁版敞鍏ワ紙闈?`by viewModels()`锛夛紝灞忓箷鏃嬭浆浼氫涪澶辩姸鎬?- 鍥剧墖/鏂囦欢浼犺緭褰撳墠涓烘枃鏈爣璁板崰浣嶏紝鐪熷疄浼犺緭闇€鎵╁睍鍗忚
- 鍓嶅彴 Service 涓洪鏋跺疄鐜帮紝GatewayClient 娉ㄥ叆寰呭畬鍠?
---

## License

MIT
