package com.agentinter.pcphone.chat

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

object CodeHighlighter {

    // 鈹€鈹€ 棰滆壊瀹氫箟 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private val keywordColor = Color(0xFF7B1FA2)      // 绱壊 鈥?鍏抽敭瀛?    private val stringColor = Color(0xFF2E7D32)        // 缁胯壊 鈥?瀛楃涓?    private val commentColor = Color(0xFF9E9E9E)       // 鐏拌壊 鈥?娉ㄩ噴
    private val numberColor = Color(0xFFE65100)        // 姗欒壊 鈥?鏁板瓧
    private val annotationColor = Color(0xFFFFD600)    // 榛勮壊 鈥?娉ㄨВ/淇グ绗?    private val functionColor = Color(0xFF1565C0)      // 钃濊壊 鈥?鍑芥暟鍚?
    // 鈹€鈹€ 璇█鍏抽敭璇?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    /** 閫氱敤鍏抽敭璇嶏紙澶у鏁拌瑷€鍏辨湁锛?*/
    private val commonKeywords = setOf(
        "if", "else", "for", "while", "do", "switch", "case",
        "return", "break", "continue", "throw", "try", "catch",
        "finally", "new", "this", "super", "true", "false", "null"
    )

    /** Kotlin 涓撳睘鍏抽敭璇?*/
    private val kotlinKeywords = commonKeywords + setOf(
        "fun", "val", "var", "class", "object", "interface",
        "data", "sealed", "enum", "when", "is", "as", "in",
        "package", "import", "override", "open", "abstract",
        "private", "protected", "public", "internal", "companion",
        "const", "lateinit", "by", "suspend", "tailrec", "inline",
        "typealias", "constructor", "init", "where", "out", "reified"
    )

    /** Java 涓撳睘鍏抽敭璇?*/
    private val javaKeywords = commonKeywords + setOf(
        "class", "interface", "extends", "implements", "abstract",
        "private", "protected", "public", "static", "final",
        "void", "int", "long", "double", "float", "boolean",
        "char", "byte", "short", "String", "package", "import",
        "throws", "synchronized", "volatile", "transient", "native",
        "instanceof", "enum", "@Override", "@Deprecated"
    )

    /** Python 涓撳睘鍏抽敭璇?*/
    private val pythonKeywords = setOf(
        "def", "class", "if", "elif", "else", "for", "while",
        "return", "import", "from", "as", "try", "except",
        "finally", "raise", "with", "yield", "lambda", "pass",
        "break", "continue", "and", "or", "not", "is", "in",
        "True", "False", "None", "self", "__init__", "print"
    )

    /** JavaScript / TypeScript 鍏抽敭璇?*/
    private val jsKeywords = commonKeywords + setOf(
        "function", "var", "let", "const", "class", "extends",
        "import", "export", "default", "from", "async", "await",
        "typeof", "instanceof", "undefined", "NaN", "Infinity",
        "console", "Promise", "async", "yield", "interface",
        "type", "enum", "readonly", "keyof"
    )

    /** JSON 鈥?鏃犲叧閿瘝锛屼絾鏁板瓧鍜屽瓧绗︿覆闇€瑕佺潃鑹?*/
    private val jsonKeywords = emptySet<String>()

    /** SQL 鍏抽敭璇?*/
    private val sqlKeywords = setOf(
        "SELECT", "FROM", "WHERE", "INSERT", "UPDATE", "DELETE",
        "CREATE", "ALTER", "DROP", "TABLE", "INDEX", "VIEW",
        "JOIN", "INNER", "LEFT", "RIGHT", "OUTER", "ON",
        "AND", "OR", "NOT", "IN", "BETWEEN", "LIKE", "IS",
        "NULL", "ORDER", "BY", "GROUP", "HAVING", "LIMIT",
        "OFFSET", "UNION", "ALL", "DISTINCT", "AS", "INTO",
        "VALUES", "SET", "PRIMARY", "KEY", "FOREIGN", "REFERENCES"
    )

    /** Shell / Bash 鍏抽敭璇?*/
    private val shellKeywords = setOf(
        "if", "then", "else", "elif", "fi", "for", "while",
        "do", "done", "case", "esac", "function", "return",
        "exit", "export", "source", "echo", "read", "local",
        "declare", "unset", "cd", "ls", "mkdir", "rm", "cp",
        "mv", "chmod", "chown", "grep", "sed", "awk", "curl",
        "wget", "git", "npm", "docker", "docker-compose"
    )

    // 鈹€鈹€ 璇█鏄犲皠 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun keywordsFor(language: String?): Set<String> = when (language?.lowercase()) {
        "kotlin", "kt"     -> kotlinKeywords
        "java"             -> javaKeywords
        "python", "py"     -> pythonKeywords
        "javascript", "js" -> jsKeywords
        "typescript", "ts" -> jsKeywords
        "json"             -> jsonKeywords
        "sql"              -> sqlKeywords
        "shell", "sh", "bash", "zsh" -> shellKeywords
        "dockerfile"       -> setOf("FROM", "RUN", "CMD", "COPY", "ADD", "EXPOSE", "ENV", "WORKDIR", "ENTRYPOINT", "VOLUME", "USER", "ARG", "LABEL")
        "yaml", "yml"       -> emptySet()  // YAML 閫氬父鏃犲叧閿瘝锛岄潬缁撴瀯鐫€鑹?        "xml", "html"       -> emptySet()
        else               -> commonKeywords  // 鏈煡璇█鍥為€€鍒伴€氱敤鍏抽敭璇?    }

    // 鈹€鈹€ 鍏叡 API 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    /**
     * 瀵逛唬鐮佹枃鏈繘琛岃娉曢珮浜紝杩斿洖 [AnnotatedString]銆?     *
     * @param code 鍘熷浠ｇ爜鏂囨湰
     * @param language 璇█鏍囪瘑锛堝 "kotlin"锛夛紝null 鍒欎粎鐢ㄩ€氱敤鍏抽敭璇?     * @return 甯﹂鑹叉爣娉ㄧ殑 AnnotatedString
     */
    fun highlight(code: String, language: String? = null): AnnotatedString {
        val keywords = keywordsFor(language)
        return buildAnnotatedString {
            val lines = code.split('\n')
            for ((lineIdx, line) in lines.withIndex()) {
                if (lineIdx > 0) append("\n")
                highlightLine(this, line, keywords)
            }
        }
    }

    // 鈹€鈹€ 鍗曡鐫€鑹?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun highlightLine(
        builder: androidx.compose.ui.text.AnnotatedString.Builder,
        line: String,
        keywords: Set<String>
    ) {
        // Step 1: 鏁磋娉ㄩ噴浼樺厛
        val trimmed = line.trimStart()
        val leadingSpace = line.length - trimmed.length

        // 鍗曡娉ㄩ噴 (// 鎴?#)
        if (trimmed.startsWith("//") || trimmed.startsWith("#")) {
            builder.withStyle(SpanStyle(color = commentColor, fontStyle = FontStyle.Italic)) {
                append(line)
            }
            return
        }

        // Step 2: 閫愯瘝鎵弿
        if (leadingSpace > 0) builder.append(line.substring(0, leadingSpace))

        var i = leadingSpace
        while (i < line.length) {
            when {
                // 鈹€鈹€ 瀛楃涓?(鍙屽紩鍙? 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                line[i] == '"' -> {
                    val end = findStringEnd(line, i, '"')
                    builder.withStyle(SpanStyle(color = stringColor)) {
                        append(line.substring(i, end + 1))
                    }
                    i = end + 1
                }

                // 鈹€鈹€ 瀛楃涓?(鍗曞紩鍙? 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                line[i] == '\'' -> {
                    val end = findStringEnd(line, i, '\'')
                    builder.withStyle(SpanStyle(color = stringColor)) {
                        append(line.substring(i, end + 1))
                    }
                    i = end + 1
                }

                // 鈹€鈹€ 琛屽唴娉ㄩ噴 // 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                i + 1 < line.length && line[i] == '/' && line[i + 1] == '/' -> {
                    builder.withStyle(SpanStyle(color = commentColor, fontStyle = FontStyle.Italic)) {
                        append(line.substring(i))
                    }
                    i = line.length
                }

                // 鈹€鈹€ 娉ㄨВ @... 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                line[i] == '@' -> {
                    val word = readWord(line, i + 1)
                    builder.withStyle(SpanStyle(color = annotationColor)) {
                        append("@$word")
                    }
                    i += 1 + word.length
                }

                // 鈹€鈹€ 鍗曡瘝 / 鏍囪瘑绗?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                line[i].isLetterOrDigit() || line[i] == '_' -> {
                    val word = readWord(line, i)
                    val color = when {
                        word in keywords -> keywordColor
                        word.toDoubleOrNull() != null -> numberColor
                        else -> null
                    }
                    if (color != null) {
                        builder.withStyle(SpanStyle(color = color, fontWeight = FontWeight.SemiBold)) {
                            append(word)
                        }
                    } else {
                        builder.append(word)
                    }
                    i += word.length
                }

                // 鈹€鈹€ 鍏朵粬瀛楃锛堢┖鏍笺€佽繍绠楃銆佹嫭鍙风瓑锛夆攢鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
                else -> {
                    builder.append(line[i])
                    i++
                }
            }
        }
    }

    // 鈹€鈹€ 杈呭姪鍑芥暟 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    /**
     * 鏌ユ壘瀛楃涓茬粨鏉熶綅缃€?     * 澶勭悊杞箟锛歕\" 涓嶇畻缁撴潫銆?     */
    private fun findStringEnd(line: String, start: Int, quote: Char): Int {
        var i = start + 1
        while (i < line.length) {
            if (line[i] == '\\') {
                i += 2  // 璺宠繃杞箟瀛楃
                continue
            }
            if (line[i] == quote) return i
            i++
        }
        return line.length - 1  // 鏈棴鍚堝瓧绗︿覆 鈫?鍒拌灏?    }

    /**
     * 浠?[start] 寮€濮嬭鍙栦竴涓爣璇嗙锛堝瓧姣?鏁板瓧/涓嬪垝绾匡級銆?     */
    private fun readWord(line: String, start: Int): String {
        var end = start
        while (end < line.length && (line[end].isLetterOrDigit() || line[end] == '_')) {
            end++
        }
        return line.substring(start, end)
    }
}
