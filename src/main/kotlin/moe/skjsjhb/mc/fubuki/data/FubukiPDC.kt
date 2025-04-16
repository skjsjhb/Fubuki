package moe.skjsjhb.mc.fubuki.data

import kotlinx.serialization.json.*
import moe.skjsjhb.mc.fubuki.data.FubukiPDC.Companion.loadFromJson
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.slf4j.LoggerFactory
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.concurrent.ConcurrentHashMap

private val logger = LoggerFactory.getLogger("Fubuki")

/**
 * An implementation of [PersistentDataContainer].
 *
 * Unlike the implementation in CraftBukkit, Fubuki uses a much simpler serialization approach based on
 * [ObjectInputStream] and [ObjectOutputStream]. This may bring some performance overhead but should be negligible.
 */
class FubukiPDC : PersistentDataContainer {
    companion object {
        fun loadFromJsonString(src: String) = FubukiPDC().apply { applyFromJsonString(src) }
        fun loadFromJson(v: JsonObject): FubukiPDC = FubukiPDC().apply { applyFromJson(v) }
    }

    private val data = ConcurrentHashMap<String, Any>() // Must pick a serializable type

    override fun <P, C> set(key: NamespacedKey, type: PersistentDataType<P, C>, value: C & Any) {
        data[key.toString()] = value
    }

    override fun <P, C> has(key: NamespacedKey, type: PersistentDataType<P, C>): Boolean =
        data[key.toString()]?.let { type.complexType.isInstance(it) } ?: false

    override fun has(key: NamespacedKey): Boolean = data.contains(key.toString())

    override fun <P, C> get(key: NamespacedKey, type: PersistentDataType<P, C>): C? =
        runCatching { type.complexType.cast(data[key.toString()]) }.getOrDefault(null)

    override fun <P, C : Any> getOrDefault(
        key: NamespacedKey,
        type: PersistentDataType<P, C>,
        defaultValue: C
    ): C =
        runCatching { type.complexType.cast(data[key.toString()]) }.getOrDefault(defaultValue)

    override fun getKeys(): MutableSet<NamespacedKey> =
        data.mapNotNull { (k, _) -> NamespacedKey.fromString(k) }.toMutableSet()

    override fun remove(key: NamespacedKey) {
        data.remove(key.toString())
    }

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun copyTo(other: PersistentDataContainer, replace: Boolean) {
        (other as FubukiPDC).data.let {
            if (replace) {
                it.putAll(data)
            } else {
                data.forEach { (k, v) ->
                    it.putIfAbsent(k, v)
                }
            }
        }
    }

    override fun getAdapterContext(): PersistentDataAdapterContext = FubukiPersistentDataAdapterContext

    fun saveAsJson(): JsonElement = JsonObject(
        data.mapValues { (_, v) -> toJson(v) }
            .filterValues { it != null }
            .mapValues { (_, v) -> v!! }
    )

    fun saveAsJsonString() = Json.encodeToString(saveAsJson())

    private fun applyFromJson(v: JsonObject) {
        data.putAll(
            v.mapValues { (_, v) -> fromJson(v) }
                .filterValues { it != null }
                .mapValues { (_, v) -> v!! }
        )
    }

    fun applyFromJsonString(src: String) {
        applyFromJson(Json.parseToJsonElement(src).jsonObject)
    }
}

@Suppress("UNCHECKED_CAST")
private fun toJson(v: Any?): JsonElement? =
    when (v) {
        is Byte -> JsonPrimitive("B${v.toString(16)}")
        is Short -> JsonPrimitive("S${v.toString(16)}")
        is Int -> JsonPrimitive(v)
        is Long -> JsonPrimitive("L${v.toString(16)}")
        is Float -> JsonPrimitive("F$v")
        is Double -> JsonPrimitive("D$v")
        is Boolean -> JsonPrimitive(v)
        is String -> JsonPrimitive("!$v")
        is ByteArray -> JsonObject(
            mapOf(
                "T" to JsonPrimitive("B"),
                "V" to JsonArray(v.map { JsonPrimitive(it) })
            )
        )

        is IntArray -> JsonObject(
            mapOf(
                "T" to JsonPrimitive("I"),
                "V" to JsonArray(v.map { JsonPrimitive(it) })
            )
        )

        is LongArray -> JsonObject(
            mapOf(
                "T" to JsonPrimitive("L"),
                "V" to JsonArray(v.map { JsonPrimitive(it) })
            )
        )

        is FubukiPDC -> JsonObject(
            mapOf(
                "T" to JsonPrimitive("C"),
                "V" to v.saveAsJson()
            )
        )

        is List<*> -> JsonArray(
            v.mapNotNull { toJson(it) }
        )

        else -> {
            if (v is Array<*> && v.all { it is FubukiPDC }) {
                JsonObject(
                    mapOf(
                        "T" to JsonPrimitive("CA"),
                        "V" to JsonArray((v as Array<FubukiPDC>).map { it.saveAsJson() })
                    )
                )
            } else {
                if (v != null) {
                    logger.warn("Ignoring unserializable type in PDC: {}", v::class.java.name)
                } else {
                    logger.warn("Ignoring null value in PDC")
                }

                null
            }
        }
    }

private fun fromJson(v: JsonElement): Any? =
    if (v is JsonPrimitive) {
        if (v.isString) {
            val header = v.content.first()
            val rest = v.content.drop(1)
            when (header) {
                'B' -> rest.toByte(16)
                'S' -> rest.toShort(16)
                'L' -> rest.toLong(16)
                'F' -> rest.toFloat()
                'D' -> rest.toDouble()
                '!' -> rest
                else -> {
                    logger.warn("Unrecognized PDC value header: {}", header)
                    null
                }
            }
        } else {
            v.booleanOrNull ?: v.intOrNull
        }
    } else if (v is JsonArray) {
        // List
        v.mapNotNull { fromJson(it) }.toMutableList()
    } else if (v is JsonObject) {
        val type = v["T"]?.jsonPrimitive?.content
        val value = v["V"]!!
        when (type) {
            "B" -> value.jsonArray.mapNotNull { it.jsonPrimitive.intOrNull?.toByte() }.toByteArray()
            "I" -> value.jsonArray.mapNotNull { it.jsonPrimitive.intOrNull }.toIntArray()
            "L" -> value.jsonArray.mapNotNull { it.jsonPrimitive.intOrNull?.toLong() }.toLongArray()
            "C" -> loadFromJson(value.jsonObject)
            "CA" -> value.jsonArray.map { loadFromJson(it.jsonObject) }.toTypedArray()
            else -> {
                logger.warn("Unrecognized PDC object header: {}", type)
                null
            }
        }
    } else null


private object FubukiPersistentDataAdapterContext : PersistentDataAdapterContext {
    override fun newPersistentDataContainer(): PersistentDataContainer = FubukiPDC()
}