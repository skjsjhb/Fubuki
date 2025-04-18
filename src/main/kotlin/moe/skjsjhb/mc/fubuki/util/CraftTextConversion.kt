package moe.skjsjhb.mc.fubuki.util

import com.google.common.collect.ImmutableMap
import moe.skjsjhb.mc.fubuki.server.FubukiServer
import net.minecraft.text.*
import net.minecraft.util.Formatting
import net.minecraft.util.Util
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Stream

/**
 * Utility class copied from CraftBukkit for converting between Mojang [Text] and formatted strings.
 */
@Suppress("RegExpUnnecessaryNonCapturingGroup", "MemberVisibilityCanBePrivate")
object CraftTextConversion {
    private val LINK_PATTERN =
        Pattern.compile("((?:(?:https?)://)?(?:[-\\w_.]{2,}\\.[a-z]{2,4}.*?(?=[.?!,;:]?(?:[" + ChatColor.COLOR_CHAR.toString() + " \\n]|$))))")

    private val formatMap: Map<Char, Formatting>

    init {
        val builder: ImmutableMap.Builder<Char, Formatting> = ImmutableMap.builder()
        Formatting.entries.forEach {
            builder.put(it.toString()[1].lowercaseChar(), it)
        }
        formatMap = builder.build()
    }

    fun getColor(color: ChatColor): Formatting? {
        return formatMap[color.char]
    }

    fun getColor(format: Formatting): ChatColor? {
        return ChatColor.getByChar(format.code)
    }

    fun fromStringOrOptional(message: String?): Optional<Text> {
        return Optional.ofNullable<Text>(fromStringOrNull(message))
    }

    fun fromStringOrOptional(message: String?, keepNewlines: Boolean): Optional<Text> {
        return Optional.ofNullable<Text>(fromStringOrNull(message, keepNewlines))
    }

    fun fromStringOrNull(message: String?): Text? {
        return fromStringOrNull(message, false)
    }

    fun fromStringOrNull(message: String?, keepNewlines: Boolean): Text? {
        return if (message.isNullOrEmpty()) null else fromString(message, keepNewlines)[0]
    }

    fun fromStringOrEmpty(message: String?): Text {
        return fromStringOrEmpty(message, false)
    }

    fun fromStringOrEmpty(message: String?, keepNewlines: Boolean): Text {
        return fromString(message, keepNewlines)[0]
    }

    fun fromString(message: String?): Array<Text> {
        return fromString(message, false)
    }

    fun fromString(message: String?, keepNewlines: Boolean): Array<Text> {
        return fromString(message, keepNewlines, false)
    }

    fun fromString(message: String?, keepNewlines: Boolean, plain: Boolean): Array<Text> {
        return StringMessage(message, keepNewlines, plain).getOutput()
    }

    fun toJSON(component: Text?): String {
        val rm = (Bukkit.getServer() as FubukiServer).toMojang().registryManager
        return Text.Serialization.toJsonString(component, rm)
    }

    fun toJSONOrNull(component: Text?): String? {
        if (component == null) return null
        return toJSON(component)
    }

    fun fromJSON(jsonMessage: String): Text? {
        // Note: This also parses plain Strings to text components.
        // Note: An empty message (empty, or only consisting of whitespace) results in null rather than a parse exception.
        val rm = (Bukkit.getServer() as FubukiServer).toMojang().registryManager
        return Text.Serialization.fromJson(jsonMessage, rm)
    }

    fun fromJSONOrNull(jsonMessage: String): Text? =
        runCatching {
            fromJSON(jsonMessage) // Can return null
        }.getOrNull()

    fun fromJSONOrString(message: String): Text? {
        return fromJSONOrString(message, false)
    }

    fun fromJSONOrString(message: String, keepNewlines: Boolean): Text? {
        return fromJSONOrString(message, false, keepNewlines)
    }

    fun fromJSONOrString(message: String, nullable: Boolean, keepNewlines: Boolean): Text? {
        return fromJSONOrString(message, nullable, keepNewlines, Int.MAX_VALUE, false)
    }

    fun fromJSONOrString(
        message: String?,
        nullable: Boolean,
        keepNewlines: Boolean,
        maxLength: Int,
        checkJsonContentLength: Boolean
    ): Text? {
        val msg = message ?: ""
        if (nullable && msg.isEmpty()) return null
        val component = fromJSONOrNull(msg)
        if (component != null) {
            if (checkJsonContentLength) {
                val content = fromComponent(component)
                val trimmedContent = trimMessage(content, maxLength)
                if (content !== trimmedContent) { // Identity comparison is fine here
                    // Note: The resulting text has all non-plain text features stripped.
                    return fromString(trimmedContent, keepNewlines)[0]
                }
            }
            return component
        } else {
            return fromString(trimMessage(msg, maxLength), keepNewlines)[0]
        }
    }

    fun trimMessage(message: String?, maxLength: Int): String? {
        return if (message != null && message.length > maxLength) {
            message.substring(0, maxLength)
        } else {
            message
        }
    }

    private fun Text.toStream(): Stream<Text> = Stream.concat(Stream.of(this), siblings.stream())

    @Suppress("KotlinConstantConditions")
    fun fromComponent(component: Text?): String {
        if (component == null) return ""
        val out = StringBuilder()

        var hadFormat = false
        for (c in component.toStream()) {
            val style = c.style
            val color: TextColor? = style.color
            if (c.content !== PlainTextContent.EMPTY || color != null) {
                if (color != null) {
                    val colorFormat = (color as TextColorFormatContainer).`fubuki$getFormat`()
                    if (colorFormat != null) {
                        out.append(colorFormat)
                    } else {
                        out.append(ChatColor.COLOR_CHAR).append("x")
                        for (magic in color.name.substring(1).toCharArray()) {
                            out.append(ChatColor.COLOR_CHAR).append(magic)
                        }
                    }
                    hadFormat = true
                } else if (hadFormat) {
                    out.append(ChatColor.RESET)
                    hadFormat = false
                }
            }
            if (style.isBold) {
                out.append(Formatting.BOLD)
                hadFormat = true
            }
            if (style.isItalic) {
                out.append(Formatting.ITALIC)
                hadFormat = true
            }
            if (style.isUnderlined) {
                out.append(Formatting.UNDERLINE)
                hadFormat = true
            }
            if (style.isStrikethrough) {
                out.append(Formatting.STRIKETHROUGH)
                hadFormat = true
            }
            if (style.isObfuscated) {
                out.append(Formatting.OBFUSCATED)
                hadFormat = true
            }
            c.content.visit {
                out.append(it)
                Optional.empty<Unit>()
            }
        }
        return out.toString()
    }

    fun fixComponent(component: MutableText): Text {
        val matcher = LINK_PATTERN.matcher("")
        return fixComponent(component, matcher)
    }

    private fun fixComponent(src: MutableText, matcher: Matcher): Text {
        var component: MutableText = src
        if (component.content is PlainTextContent) {
            val text: PlainTextContent = (component.content as PlainTextContent)
            val msg: String = text.string()
            if (matcher.reset(msg).find()) {
                matcher.reset()

                val modifier: Style = component.style
                val extras: MutableList<Text> = ArrayList<Text>()
                val extrasOld: List<Text> = ArrayList<Text>(component.siblings)
                component = Text.empty()

                var pos = 0
                while (matcher.find()) {
                    var match = matcher.group()

                    if (!(match.startsWith("http://") || match.startsWith("https://"))) {
                        match = "http://$match"
                    }

                    val prev: MutableText = Text.literal(msg.substring(pos, matcher.start()))
                    prev.setStyle(modifier)
                    extras.add(prev)

                    val link: MutableText = Text.literal(matcher.group())

                    runCatching {
                        val linkStyle = modifier.withClickEvent(ClickEvent.OpenUrl(Util.validateUri(match)))
                        link.setStyle(linkStyle)
                    }

                    extras.add(link)
                    pos = matcher.end()
                }

                val prev: MutableText = Text.literal(msg.substring(pos))
                prev.setStyle(modifier)
                extras.add(prev)
                extras.addAll(extrasOld)

                for (c in extras) {
                    component.append(c)
                }
            }
        }

        val extras: MutableList<Text> = component.siblings
        for (i in extras.indices) {
            val comp: Text = extras[i]
            if (comp.style != null && comp.style.clickEvent == null) {
                extras[i] = fixComponent(comp.copy(), matcher)
            }
        }

        if (component.content is TranslatableTextContent) {
            val subs: Array<Any> = (component.content as TranslatableTextContent).args
            for (i in subs.indices) {
                val comp = subs[i]
                if (comp is Text) {
                    val c: Text = comp
                    if (c.style != null && c.style.clickEvent == null) {
                        subs[i] = fixComponent(c.copy(), matcher)
                    }
                } else if (comp is String && matcher.reset(comp).find()) {
                    subs[i] = fixComponent(Text.literal(comp), matcher)
                }
            }
        }

        return component
    }

    private class StringMessage(private val message: String?, keepNewlines: Boolean, plain: Boolean) {
        private val list: MutableList<Text> = ArrayList()
        private var currentChatComponent = Text.empty()
        private var modifier = Style.EMPTY
        private lateinit var output: Array<Text>
        private var currentIndex = 0
        private var hex: StringBuilder? = null

        init {
            run {
                if (message == null) {
                    output = arrayOf(currentChatComponent)
                    return@run
                }
                list.add(currentChatComponent)

                val matcher = (if (keepNewlines) INCREMENTAL_PATTERN_KEEP_NEWLINES else INCREMENTAL_PATTERN).matcher(
                    message
                )
                var match: String?
                var needsAdd = false
                while (matcher.find()) {
                    var groupId = 0
                    while ((matcher.group(++groupId).also { match = it }) == null) {
                        // NOOP
                    }
                    val index = matcher.start(groupId)
                    if (index > currentIndex) {
                        needsAdd = false
                        appendNewComponent(index)
                    }
                    when (groupId) {
                        1 -> {
                            val c = match!!.lowercase()[1]
                            val format = formatMap.getValue(c)

                            if (c == 'x') {
                                hex = StringBuilder("#")
                            } else if (hex != null) {
                                hex!!.append(c)

                                if (hex!!.length == 7) {
                                    modifier = RESET.withColor(TextColor.parse(hex.toString()).result().get())
                                    hex = null
                                }
                            } else if (format.isModifier && format !== Formatting.RESET) {
                                modifier = when (format) {
                                    Formatting.BOLD -> modifier.withBold(java.lang.Boolean.TRUE)
                                    Formatting.ITALIC -> modifier.withItalic(java.lang.Boolean.TRUE)
                                    Formatting.STRIKETHROUGH -> modifier.withStrikethrough(java.lang.Boolean.TRUE)
                                    Formatting.UNDERLINE -> modifier.withUnderline(java.lang.Boolean.TRUE)
                                    Formatting.OBFUSCATED -> modifier.withObfuscated(java.lang.Boolean.TRUE)
                                    else -> throw AssertionError("Unexpected message format")
                                }
                            } else { // Color resets formatting
                                modifier = RESET.withColor(format)
                            }
                            needsAdd = true
                        }

                        2 -> if (plain) {
                            appendNewComponent(matcher.end(groupId))
                        } else {
                            if (!(match!!.startsWith("http://") || match!!.startsWith("https://"))) {
                                match = "http://$match"
                            }
                            runCatching {
                                modifier = modifier.withClickEvent(
                                    ClickEvent.OpenUrl(
                                        Util.validateUri(
                                            match
                                        )
                                    )
                                )
                            }
                            appendNewComponent(matcher.end(groupId))
                            modifier = modifier.withClickEvent(null)
                        }

                        3 -> {
                            if (needsAdd) {
                                appendNewComponent(index)
                            }
                            currentChatComponent = null
                        }
                    }
                    currentIndex = matcher.end(groupId)
                }

                if (currentIndex < message.length || needsAdd) {
                    appendNewComponent(message.length)
                }

                output = list.toTypedArray()
            }
        }

        fun appendNewComponent(index: Int) {
            val addition: Text =
                Text.literal(message!!.substring(currentIndex, index)).setStyle(modifier)
            currentIndex = index
            if (currentChatComponent == null) {
                currentChatComponent = Text.empty()
                list.add(currentChatComponent)
            }
            currentChatComponent.append(addition)
        }

        fun getOutput(): Array<Text> {
            return output
        }

        companion object {
            private val INCREMENTAL_PATTERN: Pattern = Pattern.compile(
                "(" + ChatColor.COLOR_CHAR + "[0-9a-fk-orx])|((?:(?:https?)://)?(?:[-\\w_.]{2,}\\.[a-z]{2,4}.*?(?=[.?!,;:]?(?:[" + ChatColor.COLOR_CHAR + " \\n]|$))))|(\\n)",
                Pattern.CASE_INSENSITIVE
            )

            private val INCREMENTAL_PATTERN_KEEP_NEWLINES: Pattern = Pattern.compile(
                "(" + ChatColor.COLOR_CHAR + "[0-9a-fk-orx])|((?:(?:https?)://)?(?:[-\\w_.]{2,}\\.[a-z]{2,4}.*?(?=[.?!,;:]?(?:[" + ChatColor.COLOR_CHAR + " ]|$))))",
                Pattern.CASE_INSENSITIVE
            )


            private val RESET: Style =
                Style.EMPTY.withBold(false).withItalic(false).withUnderline(false).withStrikethrough(false)
                    .withObfuscated(false)
        }
    }
}