package com.agentinter.pcphone.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.agentinter.pcphone.core.model.AttachmentType
import com.agentinter.pcphone.core.model.Message
import com.agentinter.pcphone.core.model.MessageRole
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  Markdown 妫€娴?// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * Markdown 璇硶鐗瑰緛姝ｅ垯銆? *
 * 鍖归厤甯歌鐨?Markdown 鏍囪锛? * - ```               鈫?浠ｇ爜鍧楀洿鏍? * - ** 鎴?__           鈫?鍔犵矖
 * - `                  鈫?琛屽唴浠ｇ爜
 * - 琛岄 #             鈫?鏍囬
 * - 琛岄 - 鎴?*        鈫?鏃犲簭鍒楄〃
 * - 琛岄 >             鈫?寮曠敤
 * - [text](url)        鈫?閾炬帴
 * - 琛岄 鏁板瓧.         鈫?鏈夊簭鍒楄〃
 */
private val MARKDOWN_PATTERN = Regex(
    "(```|\\*\\*|__|`|^#{1,6}\\s|^[-*]\\s|^>\\s|\\[.+\\]\\(.+\\)|^\\d+\\.\\s)",
    setOf(RegexOption.MULTILINE)
)

/** 鍒ゆ柇鏂囨湰鏄惁鍖呭惈 Markdown 璇硶 */
private fun isMarkdown(text: String): Boolean {
    return MARKDOWN_PATTERN.containsMatchIn(text)
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  鏃堕棿鏍煎紡鍖?// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/** 绾跨▼灞€閮ㄧ紦瀛橈紝閬垮厤姣忔鏍煎紡鍖栨柊寤?SimpleDateFormat */
private val timeFormatter = ThreadLocal.withInitial {
    SimpleDateFormat("HH:mm", Locale.getDefault())
}

/**
 * 娑堟伅姘旀场缁勪欢
 *
 * 鐢ㄦ埛娑堟伅 鈫?钃濊壊姘旀场锛屽彸瀵归綈
 * AI 娑堟伅 鈫?鐏拌壊姘旀场锛屽乏瀵归綈
 * 搴曢儴鏄剧ず鐩稿鏃堕棿鎴? *
 * AI 娑堟伅涓殑 Markdown锛堜唬鐮佸潡銆佹爣棰樸€佸垪琛ㄧ瓑锛夎嚜鍔ㄦ覆鏌撲负鏍煎紡鍖栧唴瀹广€? * 鐢ㄦ埛娑堟伅鍑犱箮涓嶅惈 Markdown锛屽嵆浣垮寘鍚篃浣跨敤榛樿涓婚鑹叉覆鏌撱€? */
@Composable
fun MessageItem(message: Message) {
    val isUser = message.role == MessageRole.User
    var previewPath by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.widthIn(max = 300.dp),
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ) {
            // 鍥剧墖闄勪欢缂╃暐鍥?            message.attachments.forEach { attachment ->
                if (attachment.type == AttachmentType.Image && attachment.localPath != null) {
                    val bitmap = remember(attachment.localPath) {
                        BitmapFactory.decodeFile(attachment.localPath!!)
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "鍥剧墖",
                            modifier = Modifier
                                .widthIn(max = 250.dp)
                                .heightIn(max = 200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { previewPath = attachment.localPath },
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            // 姘旀场
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                color = if (isUser)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                shadowElevation = 1.dp
            ) {
                if (isMarkdown(message.content)) {
                    // Markdown 娓叉煋锛堝甫缂撳瓨锛岄伩鍏嶉噸缁勬椂閲嶅瑙ｆ瀽锛?                    val blocks = remember(message.content) {
                        MarkdownParser.parse(message.content)
                    }
                    MarkdownRenderer(
                        blocks = blocks,
                        modifier = Modifier.padding(12.dp)
                    )
                } else {
                    // 绾枃鏈?鈥?鐩存帴浣跨敤 Text 缁勪欢锛屾€ц兘鏇翠紭
                    Text(
                        text = message.content,
                        modifier = Modifier.padding(12.dp),
                        color = if (isUser)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // 鍏ㄥ睆鍥剧墖棰勮
            previewPath?.let { path ->
                ImageViewer(imagePath = path, onDismiss = { previewPath = null })
            }

            // 鏃堕棿鎴?            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = formatRelativeTime(message.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * 鐩稿鏃堕棿鏍煎紡鍖? *
 * < 1鍒嗛挓 鈫?"鍒氬垰"
 * < 1灏忔椂 鈫?"N鍒嗛挓鍓?
 * < 1澶?  鈫?"N灏忔椂鍓?
 * 鈮?1澶?  鈫?"HH:mm"
 */
private fun formatRelativeTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "鍒氬垰"
        diff < 3_600_000 -> "${diff / 60_000}鍒嗛挓鍓?
        diff < 86_400_000 -> "${diff / 3_600_000}灏忔椂鍓?
        else -> timeFormatter.get().format(Date(timestamp))
    }
}
