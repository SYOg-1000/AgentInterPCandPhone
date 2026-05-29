package com.agentinter.pcphone.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.agentinter.pcphone.ui.theme.CodeFontFamily

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  MarkdownRenderer 鈥?灏?MarkdownBlock 鍒楄〃娓叉煋涓?Compose UI
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * Markdown 娓叉煋鍣ㄣ€? *
 * 灏?[MarkdownParser.parse] 杈撳嚭鐨?[MarkdownBlock] 鍒楄〃娓叉煋涓哄畬鏁寸殑 Compose UI銆? * 鏀寔鎵€鏈?7 绉嶅潡绫诲瀷锛欻eading銆丳aragraph銆丆odeBlock銆乁norderedList銆丱rderedList銆? * Blockquote銆乀hematicBreak銆? *
 * 鎵€鏈?block 涔嬮棿鑷姩娣诲姞 8dp 闂磋窛锛屾暣浣撳彲鍨傜洿婊氬姩銆? *
 * @param blocks   MarkdownBlock 鍒楄〃
 * @param modifier 澶栭儴 Modifier
 */
@Composable
fun MarkdownRenderer(
    blocks: List<MarkdownBlock>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        blocks.forEachIndexed { index, block ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            RenderBlock(block)
        }
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  Block 鍒嗗彂
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

@Composable
private fun RenderBlock(block: MarkdownBlock) {
    when (block) {
        is MarkdownBlock.Heading       -> RenderHeading(block)
        is MarkdownBlock.Paragraph     -> RenderParagraph(block)
        is MarkdownBlock.CodeBlock     -> RenderCodeBlock(block)
        is MarkdownBlock.UnorderedList -> RenderUnorderedList(block)
        is MarkdownBlock.OrderedList   -> RenderOrderedList(block)
        is MarkdownBlock.Blockquote    -> RenderBlockquote(block)
        is MarkdownBlock.ThematicBreak -> RenderThematicBreak()
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  Heading
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 鏍囬娓叉煋銆傚瓧鍙锋寜 level 閫掑噺銆? *
 * level 1 鈫?headlineLarge (28sp)
 * level 2 鈫?headlineMedium (22sp)
 * level 3 鈫?titleLarge    (18sp)
 * level 4-6 鈫?titleMedium (16sp)
 */
@Composable
private fun RenderHeading(heading: MarkdownBlock.Heading) {
    val style = when (heading.level) {
        1    -> MaterialTheme.typography.headlineLarge
        2    -> MaterialTheme.typography.headlineMedium
        3    -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleMedium
    }

    Text(
        text = heading.text,
        style = style,
        modifier = Modifier.fillMaxWidth()
    )
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  Paragraph 鈫?InlineText
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 娈佃惤娓叉煋銆傝鍐呭厓绱犻€氳繃 [InlineText] 娓叉煋涓?AnnotatedString銆? */
@Composable
private fun RenderParagraph(paragraph: MarkdownBlock.Paragraph) {
    InlineText(
        inlines = paragraph.inlines,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * 琛屽唴鏂囨湰缁勪欢銆傚皢 [InlineElement] 鍒楄〃娓叉煋涓哄甫鏍峰紡鐨?[Text]銆? *
 * 鏀寔鏍峰紡锛? * - Text(content)       鈫?绾枃鏈? * - Bold(content)       鈫?鍔犵矖
 * - Italic(content)     鈫?鏂滀綋
 * - Code(content)       鈫?绛夊瀛椾綋 + surfaceVariant 鑳屾櫙
 * - Link(text, url)     鈫?primary 棰滆壊 + 涓嬪垝绾? *
 * 棰滆壊鍦?@Composable 浣滅敤鍩熶腑瑙ｆ瀽锛屽啀浼犻€掔粰鏋勫缓鍑芥暟銆? */
@Composable
fun InlineText(
    inlines: List<InlineElement>,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    val primary = MaterialTheme.colorScheme.primary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Text(
        text = buildInlineAnnotatedString(
            inlines = inlines,
            linkColor = primary,
            codeBackground = surfaceVariant
        ),
        modifier = modifier,
        style = style
    )
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  buildInlineAnnotatedString 鈥?閫掑綊鏋勫缓 AnnotatedString
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 閫掑綊鍦板皢琛屽唴鍏冪礌鍒楄〃鏋勫缓涓?[AnnotatedString]銆? *
 * 棰滆壊鍙傛暟鐢辫皟鐢ㄦ柟锛園Composable 浣滅敤鍩燂級浼犲叆锛岄伩鍏嶅湪闈?Composable Lambda
 * 涓闂?MaterialTheme銆? *
 * @param inlines       琛屽唴鍏冪礌鍒楄〃
 * @param linkColor     閾炬帴鏂囧瓧棰滆壊锛堥€氬父涓?primary锛? * @param codeBackground 琛屽唴浠ｇ爜鑳屾櫙鑹诧紙閫氬父涓?surfaceVariant锛? * @return 鍖呭惈鏍峰紡鏍囨敞鐨?AnnotatedString
 */
fun buildInlineAnnotatedString(
    inlines: List<InlineElement>,
    linkColor: Color = Color.Unspecified,
    codeBackground: Color = Color.Unspecified
): AnnotatedString {
    return buildAnnotatedString {
        for (element in inlines) {
            appendInlineElement(this, element, linkColor, codeBackground)
        }
    }
}

/**
 * 灏嗗崟涓?[InlineElement] 杩藉姞鍒?[AnnotatedString.Builder] 涓€? * 瀵?Bold / Italic 閫掑綊澶勭悊鍏跺瓙鍏冪礌銆? */
private fun appendInlineElement(
    builder: AnnotatedString.Builder,
    element: InlineElement,
    linkColor: Color,
    codeBackground: Color
) {
    when (element) {
        is InlineElement.Text -> {
            builder.append(element.content)
        }

        is InlineElement.Bold -> {
            builder.withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                for (child in element.content) {
                    appendInlineElement(this, child, linkColor, codeBackground)
                }
            }
        }

        is InlineElement.Italic -> {
            builder.withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                for (child in element.content) {
                    appendInlineElement(this, child, linkColor, codeBackground)
                }
            }
        }

        is InlineElement.Code -> {
            builder.withStyle(
                SpanStyle(
                    fontFamily = CodeFontFamily,
                    background = codeBackground
                )
            ) {
                builder.append(element.content)
            }
        }

        is InlineElement.Link -> {
            builder.withStyle(
                SpanStyle(
                    color = linkColor,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                builder.append(element.text)
            }
        }
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  CodeBlock
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 浠ｇ爜鍧楁覆鏌撱€? *
 * - 浣跨敤 [CodeHighlighter.highlight] 鐢熸垚璇硶楂樹寒 AnnotatedString
 * - 娣辫壊鑳屾櫙锛坰urfaceVariant锛? * - 12dp 鍐呰竟璺? * - [SelectionContainer] 鍖呰９浠ユ敮鎸佹枃鏈€夋嫨
 * - 鍙充笂瑙掓樉绀鸿瑷€鏍囩
 * - 妯悜鍙粴鍔? */
@Composable
private fun RenderCodeBlock(codeBlock: MarkdownBlock.CodeBlock) {
    val highlighted = CodeHighlighter.highlight(
        code = codeBlock.code,
        language = codeBlock.language
    )

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            // 鈹€鈹€ 璇█鏍囩锛堝彸涓婅锛?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            if (codeBlock.language != null) {
                Text(
                    text = codeBlock.language,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                )
            }

            // 鈹€鈹€ 浠ｇ爜鍐呭锛堝彲婊氬姩銆佸彲閫夋嫨锛?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
            SelectionContainer {
                Text(
                    text = highlighted,
                    fontFamily = CodeFontFamily,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .horizontalScroll(rememberScrollState())
                )
            }
        }
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  UnorderedList
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 鏃犲簭鍒楄〃銆傛瘡椤逛互 "鈥?" 寮€澶达紝宸﹁竟缂╄繘 16dp銆? */
@Composable
private fun RenderUnorderedList(list: MarkdownBlock.UnorderedList) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        list.items.forEach { itemInlines ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "鈥?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                InlineText(
                    inlines = itemInlines,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  OrderedList
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 鏈夊簭鍒楄〃銆傛瘡椤逛互 "$index. " 寮€澶达紝宸﹁竟缂╄繘 16dp銆? * 绱㈠紩浠?[MarkdownBlock.OrderedList.start] 寮€濮嬮€掑銆? */
@Composable
private fun RenderOrderedList(list: MarkdownBlock.OrderedList) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        list.items.forEachIndexed { index, itemInlines ->
            val number = list.start + index
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$number. ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                InlineText(
                    inlines = itemInlines,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  Blockquote
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 寮曠敤鍧椼€? *
 * 宸︿晶 4dp 绔栫嚎锛坧rimary 鑹诧紝30% 閫忔槑搴︼級+ surfaceVariant 鍗婇€忔槑鑳屾櫙銆? * 鍐呭閫掑綊璋冪敤 [MarkdownRenderer]銆? */
@Composable
private fun RenderBlockquote(blockquote: MarkdownBlock.Blockquote) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        // 鈹€鈹€ 宸︿晶绔栫嚎 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 鈹€鈹€ 寮曠敤鍐呭锛堥€掑綊娓叉煋锛?鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
        MarkdownRenderer(
            blocks = blockquote.blocks,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp, horizontal = 4.dp)
        )
    }
}

// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€
//  ThematicBreak
// 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

/**
 * 鍒嗛殧绾裤€備娇鐢?[HorizontalDivider]锛屼笂涓?8dp 鍐呰竟璺濄€? */
@Composable
private fun RenderThematicBreak() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        )
    }
}
