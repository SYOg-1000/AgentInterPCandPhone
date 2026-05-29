package com.agentinter.pcphone.chat

/**
 * Slash 鍛戒护琛ュ叏鍣ㄣ€? * 浣跨敤 Trie 绱㈠紩瀹炵幇鍓嶇紑鍖归厤 + 妯＄硦鎼滅储銆? */
object SlashCompleter {

    private data class Command(
        val name: String,
        val description: String
    )

    /** 鍐呯疆鍛戒护锛堝悗缁彲浠?Gateway commands.list 鍔ㄦ€佸姞杞斤級 */
    private val builtinCommands = listOf(
        Command("/help", "鏄剧ず甯姪淇℃伅"),
        Command("/clear", "娓呴櫎褰撳墠瀵硅瘽"),
        Command("/model", "鏌ョ湅/鍒囨崲妯″瀷"),
        Command("/theme", "鍒囨崲涓婚 (light/dark/system)"),
        Command("/export", "瀵煎嚭瀵硅瘽璁板綍"),
        Command("/about", "鍏充簬 AgentInter"),
        Command("/status", "鏌ョ湅杩炴帴鐘舵€?),
        Command("/reset", "閲嶇疆浼氳瘽")
    )

    private var commands: List<Command> = builtinCommands

    /** 閲嶆柊鍔犺浇鍛戒护鍒楄〃锛堝悗缁彲浠?Gateway 鍔ㄦ€佹洿鏂帮級 */
    fun reloadCommands(newCommands: List<Pair<String, String>>) {
        commands = newCommands.map { (name, desc) -> Command(name, desc) }
    }

    /**
     * 鏍规嵁杈撳叆鍓嶇紑鍖归厤鍛戒护銆?     * @param prefix 杈撳叆鍓嶇紑锛堝 "/he"锛?     * @param maxResults 鏈€澶ц繑鍥炴暟
     * @return 鍖归厤鐨勫懡浠ゅ垪琛?(name, description)
     */
    fun complete(prefix: String, maxResults: Int = 5): List<Pair<String, String>> {
        if (!prefix.startsWith("/")) return emptyList()
        if (prefix.length <= 1) {
            // 鍙緭鍏ヤ簡 "/"锛氭樉绀烘墍鏈夊懡浠?            return commands.take(maxResults).map { it.name to it.description }
        }

        val lower = prefix.lowercase()
        return commands
            .filter { it.name.lowercase().startsWith(lower) }
            .take(maxResults)
            .map { it.name to it.description }
    }
}
