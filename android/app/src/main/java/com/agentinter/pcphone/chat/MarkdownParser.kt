package com.agentinter.pcphone.chat

/**
 * Markdown 鍧楃骇鍏冪礌
 */
sealed class MarkdownBlock {
    /** 鏍囬 (level: 1-6) */
    data class Heading(val level: Int, val text: String) : MarkdownBlock()

    /** 鏅€氭钀斤紙鍚鍐呮牱寮忥級 */
    data class Paragraph(val inlines: List<InlineElement>) : MarkdownBlock()

    /** 浠ｇ爜鍧?*/
    data class CodeBlock(val language: String?, val code: String) : MarkdownBlock()

    /** 鏃犲簭鍒楄〃 */
    data class UnorderedList(val items: List<List<InlineElement>>) : MarkdownBlock()

    /** 鏈夊簭鍒楄〃 */
    data class OrderedList(val items: List<List<InlineElement>>, val start: Int = 1) : MarkdownBlock()

    /** 寮曠敤鍧楋紙鍙祵濂楀叾浠?block锛?*/
    data class Blockquote(val blocks: List<MarkdownBlock>) : MarkdownBlock()

    /** 鍒嗛殧绾?(--- 鎴?***) */
    object ThematicBreak : MarkdownBlock()
}

/**
 * 琛屽唴鍏冪礌
 */
sealed class InlineElement {
    /** 绾枃鏈?*/
    data class Text(val content: String) : InlineElement()
    /** 鍔犵矖 **text** */
    data class Bold(val content: List<InlineElement>) : InlineElement()
    /** 鏂滀綋 *text* */
    data class Italic(val content: List<InlineElement>) : InlineElement()
    /** 琛屽唴浠ｇ爜 `code` */
    data class Code(val content: String) : InlineElement()
    /** 閾炬帴 [text](url) */
    data class Link(val text: String, val url: String) : InlineElement()
}

/**
 * 绾枃鏈?Markdown 瑙ｆ瀽鍣ㄣ€? * 鑱岃矗锛歁arkdown 鏂囨湰 鈫?[MarkdownBlock] 鍒楄〃銆? * 涓嶅寘鍚换浣?Compose/UI 浠ｇ爜銆? */
object MarkdownParser {

    // 鈹€鈹€ 鍏叡 API 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    /**
     * 瑙ｆ瀽 Markdown 鏂囨湰涓哄潡绾у厓绱犲垪琛ㄣ€?     *
     * @param text 鍘熷 Markdown 鏂囨湰
     * @return 鍧楃骇鍏冪礌鍒楄〃锛堢┖杈撳叆杩斿洖绌哄垪琛級
     */
    fun parse(text: String): List<MarkdownBlock> {
        val normalized = normalize(text)
        if (normalized.isEmpty()) return emptyList()

        val lines = normalized.split('\n')
        val blocks = mutableListOf<MarkdownBlock>()
        var i = 0

        while (i < lines.size) {
            // 鈹€鈹€ 浠ｇ爜鍧楋紙鏈€楂樹紭鍏堢骇锛夆攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            if (isCodeFence(lines[i])) {
                val (codeBlock, nextIndex) = parseCodeBlock(lines, i)
                blocks.add(codeBlock)
                i = nextIndex
                continue
            }

            // 鈹€鈹€ 绱Н闈炰唬鐮佽 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            val blockLines = mutableListOf<String>()
            while (i < lines.size) {
                if (isCodeFence(lines[i])) break
                if (blockLines.isNotEmpty() && lines[i].trim().isEmpty()) {
                    i++ // 璺宠繃绌鸿
                    break
                }
                if (lines[i].trim().isEmpty()) {
                    i++
                    continue
                }
                blockLines.add(lines[i])
                i++
            }

            if (blockLines.isEmpty()) continue

            val block = classifyBlock(blockLines.joinToString("\n"))
            if (block != null) {
                blocks.add(block)
            }
        }

        return blocks
    }

    /**
     * 瑙ｆ瀽琛屽唴 Markdown锛堟钀藉唴銆佸垪琛ㄩ」鍐咃級銆?     * 鏀寔: **bold**, *italic*, `code`, [link](url)
     *
     * @param text 琛屽唴鏂囨湰
     * @return 琛屽唴鍏冪礌鍒楄〃
     */
    fun parseInlines(text: String): List<InlineElement> {
        val result = mutableListOf<InlineElement>()
        val sb = StringBuilder()

        fun flushText() {
            if (sb.isNotEmpty()) {
                result.add(InlineElement.Text(sb.toString()))
                sb.clear()
            }
        }

        var i = 0
        while (i < text.length) {
            val c = text[i]

            when {
                // 鈹€鈹€ `code` 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                c == '`' -> {
                    val end = text.indexOf('`', i + 1)
                    if (end != -1) {
                        flushText()
                        result.add(InlineElement.Code(text.substring(i + 1, end)))
                        i = end
                    } else {
                        // 涓嶅尮閰嶇殑鍙嶅紩鍙?鈫?鏅€氭枃鏈?                        sb.append(c)
                    }
                }

                // 鈹€鈹€ **bold** 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                c == '*' && i + 1 < text.length && text[i + 1] == '*' -> {
                    val end = findBoldEnd(text, i + 2)
                    if (end != -1) {
                        flushText()
                        val inner = parseInlines(text.substring(i + 2, end))
                        result.add(InlineElement.Bold(inner))
                        i = end + 1 // 璺宠繃 closing **
                    } else {
                        // 涓嶅尮閰嶇殑 ** 鈫?鏅€氭枃鏈?                        sb.append("**")
                        i++
                    }
                }

                // 鈹€鈹€ *italic* 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                c == '*' -> {
                    val end = findItalicEnd(text, i + 1)
                    if (end != -1) {
                        flushText()
                        val inner = parseInlines(text.substring(i + 1, end))
                        result.add(InlineElement.Italic(inner))
                        i = end
                    } else {
                        // 涓嶅尮閰嶇殑 * 鈫?鏅€氭枃鏈?                        sb.append(c)
                    }
                }

                // 鈹€鈹€ [link](url) 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                c == '[' -> {
                    val closeBracket = text.indexOf(']', i + 1)
                    if (closeBracket != -1 &&
                        closeBracket + 1 < text.length &&
                        text[closeBracket + 1] == '('
                    ) {
                        val closeParen = text.indexOf(')', closeBracket + 2)
                        if (closeParen != -1) {
                            flushText()
                            val linkText = text.substring(i + 1, closeBracket)
                            val url = text.substring(closeBracket + 2, closeParen)
                            result.add(InlineElement.Link(linkText, url))
                            i = closeParen
                        } else {
                            sb.append(c)
                        }
                    } else {
                        sb.append(c)
                    }
                }

                else -> sb.append(c)
            }

            i++
        }

        flushText()
        return result
    }

    // 鈹€鈹€ 绉佹湁杈呭姪 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    /** 缁熶竴鎹㈣绗︺€佸幓闄ら灏剧┖鐧?*/
    private fun normalize(text: String): String {
        return text
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .trim()
    }

    /** 鍒ゆ柇琛屾槸鍚︿负浠ｇ爜鍥存爮寮€/鍏?*/
    private fun isCodeFence(line: String): Boolean {
        val trimmed = line.trim()
        return trimmed.startsWith("```") && trimmed.count { it == '`' } >= 3
    }

    /** 瑙ｆ瀽涓€涓畬鏁寸殑浠ｇ爜鍧楋紝杩斿洖 (CodeBlock, 涓嬩竴琛岀储寮? */
    private fun parseCodeBlock(lines: List<String>, start: Int): Pair<MarkdownBlock.CodeBlock, Int> {
        val firstLine = lines[start].trim()
        val language = firstLine.removePrefix("```").trim().ifEmpty { null }

        val codeLines = mutableListOf<String>()
        var i = start + 1
        while (i < lines.size && !isCodeFence(lines[i])) {
            codeLines.add(lines[i])
            i++
        }
        // i 鎸囧悜 closing ```锛堟垨瓒婄晫锛?        val code = codeLines.joinToString("\n")
        return MarkdownBlock.CodeBlock(language, code) to (i + 1)
    }

    /** 灏嗕竴娈电函鏂囨湰褰掔被涓哄搴旂殑 [MarkdownBlock] */
    private fun classifyBlock(text: String): MarkdownBlock? {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return null

        val lines = trimmed.split('\n')
        val firstLine = lines.first().trim()

        // 鈹€鈹€ ThematicBreak 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        if (lines.size == 1 && isThematicBreak(firstLine)) {
            return MarkdownBlock.ThematicBreak
        }

        // 鈹€鈹€ Heading 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        if (lines.size == 1) {
            val heading = parseHeading(firstLine)
            if (heading != null) return heading
        }

        // 鈹€鈹€ Blockquote 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        if (lines.all { it.trimStart().startsWith(">") }) {
            val content = lines.joinToString("\n") { line ->
                val trimmedLine = line.trimStart()
                when {
                    trimmedLine.startsWith("> ") -> trimmedLine.removePrefix("> ")
                    trimmedLine.startsWith(">")  -> trimmedLine.removePrefix(">")
                    else                         -> trimmedLine
                }
            }
            val innerBlocks = parse(content)
            return MarkdownBlock.Blockquote(innerBlocks)
        }

        // 鈹€鈹€ UnorderedList 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        if (lines.all { isUnorderedListItem(it) }) {
            val items = lines.map { line ->
                val text = line.trimStart().removePrefix("- ").removePrefix("* ").trim()
                parseInlines(text)
            }
            return MarkdownBlock.UnorderedList(items)
        }

        // 鈹€鈹€ OrderedList 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        val orderedRegex = Regex("^\\s*(\\d+)\\.\\s")
        if (lines.all { orderedRegex.containsMatchIn(it.trimStart()) }) {
            val firstMatch = orderedRegex.find(lines.first().trimStart())
            val start = firstMatch?.groupValues?.get(1)?.toIntOrNull() ?: 1
            val items = lines.map { line ->
                val text = line.trimStart().replaceFirst(orderedRegex, "")
                parseInlines(text)
            }
            return MarkdownBlock.OrderedList(items, start)
        }

        // 鈹€鈹€ 榛樿锛歅aragraph 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        return MarkdownBlock.Paragraph(parseInlines(trimmed))
    }

    /** 鍒ゆ柇鏄惁涓哄垎闅旂嚎 */
    private fun isThematicBreak(line: String): Boolean {
        val trimmed = line.trim()
        if (trimmed.length < 3) return false
        val firstChar = trimmed.first()
        if (firstChar !in setOf('-', '*', '_')) return false
        return trimmed.all { it == firstChar }
    }

    /** 鍒ゆ柇鏄惁涓烘棤搴忓垪琛ㄩ」琛岋紙-  鎴?*  寮€澶达級 */
    private fun isUnorderedListItem(line: String): Boolean {
        val trimmed = line.trimStart()
        return trimmed.startsWith("- ") || trimmed.startsWith("* ")
    }

    /** 瑙ｆ瀽鏍囬琛岋紝闈炴爣棰樿杩斿洖 null */
    private fun parseHeading(line: String): MarkdownBlock.Heading? {
        val trimmed = line.trim()
        var level = 0
        for (ch in trimmed) {
            if (ch == '#') level++ else break
        }
        if (level in 1..6 && trimmed.length > level && trimmed[level] == ' ') {
            val text = trimmed.substring(level + 1).trim()
            return MarkdownBlock.Heading(level, text)
        }
        return null
    }

    /**
     * 鍦?text 涓粠 start 寮€濮嬫煡鎵?closing **銆?     * @return closing ** 鐨勭涓€涓瓧绗︾殑绱㈠紩锛屾湭鎵惧埌杩斿洖 -1
     */
    private fun findBoldEnd(text: String, start: Int): Int {
        var i = start
        while (i + 1 < text.length) {
            if (text[i] == '*' && text[i + 1] == '*') {
                return i
            }
            i++
        }
        return -1
    }

    /**
     * 鍦?text 涓粠 start 寮€濮嬫煡鎵?closing *锛堥潪 ** 鐨勪竴閮ㄥ垎锛夈€?     * @return 鍗曚釜 * 鐨勭储寮曪紝鏈壘鍒拌繑鍥?-1
     */
    private fun findItalicEnd(text: String, start: Int): Int {
        var i = start
        while (i < text.length) {
            if (text[i] == '*') {
                // 纭繚涓嶆槸 ** 鐨勪竴閮ㄥ垎
                if (i + 1 >= text.length || text[i + 1] != '*') {
                    return i
                }
            }
            i++
        }
        return -1
    }
}

/* ================================================================
 *  娴嬭瘯鐢ㄤ緥锛堜粎浣滀负娉ㄩ噴鍙傝€冿紝涓嶅疄闄呯紪璇戞墽琛岋級
 * ================================================================
 *
 * 杈撳叆: "# Hello"
 * 杈撳嚭: [Heading(1, "Hello")]
 *
 * 杈撳叆: "Hello **world**"
 * 杈撳嚭: [Paragraph([Text("Hello "), Bold([Text("world")])])]
 *
 * 杈撳叆: "```kotlin\nfun main() {}\n```"
 * 杈撳嚭: [CodeBlock("kotlin", "fun main() {}")]
 *
 * 杈撳叆: "- item1\n- item2"
 * 杈撳嚭: [UnorderedList([[Text("item1")], [Text("item2")]])]
 *
 * 杈撳叆: "> quoted"
 * 杈撳嚭: [Blockquote([Paragraph([Text("quoted")])])]
 *
 * 杈撳叆: "`code` and **bold** and *italic* and [link](https://x.com)"
 * 杈撳嚭: [Paragraph([Code("code"), Text(" and "), Bold([Text("bold")]),
 *                  Text(" and "), Italic([Text("italic")]),
 *                  Text(" and "), Link("link", "https://x.com")])]
 */
