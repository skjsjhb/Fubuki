package moe.skjsjhb.mc.fubuki.data

import net.jpountz.lz4.LZ4Factory
import net.jpountz.lz4.LZ4FrameInputStream
import net.jpountz.lz4.LZ4FrameOutputStream
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.slf4j.LoggerFactory
import java.io.*
import java.util.concurrent.ConcurrentHashMap

/**
 * An implementation of [PersistentDataContainer].
 *
 * Unlike the implementation in CraftBukkit, Fubuki uses a much simpler serialization approach based on
 * [ObjectInputStream] and [ObjectOutputStream]. This may bring some performance overhead but should be negligible.
 */
class FubukiPDC : PersistentDataContainer, Serializable {
    companion object {
        private val lz4Factory = LZ4Factory.fastestJavaInstance()
        private val lz4Compressor = lz4Factory.fastCompressor()
        private val lz4Decompressor = lz4Factory.safeDecompressor()
        private val logger = LoggerFactory.getLogger("Fubuki")
        private const val serialVersionUID = 1L
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

    fun saveAsByteArray(): ByteArray? {
        runCatching {
            val bos = ByteArrayOutputStream()
            ObjectOutputStream(LZ4FrameOutputStream(bos)).use {
                it.writeObject(data)
            }
            return bos.toByteArray().also { logger.info("PDC data size: ${it.size}") }
        }.onFailure {
            logger.error("Failed to write PDC data", it)
        }

        return null
    }

    @Suppress("UNCHECKED_CAST")
    fun loadFromByteArray(src: ByteArray) {
        runCatching {
            ObjectInputStream(LZ4FrameInputStream(ByteArrayInputStream(src))).use {
                data.putAll(it.readObject() as Map<String, Any>)
            }
        }.onFailure {
            logger.error("Failed to load PDC data", it)
        }
    }
}

private object FubukiPersistentDataAdapterContext : PersistentDataAdapterContext {
    override fun newPersistentDataContainer(): PersistentDataContainer = FubukiPDC()
}