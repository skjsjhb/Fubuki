package moe.skjsjhb.mc.fubuki.data

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
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

    fun saveAsByteArray(): ByteArray {
        val bos = ByteArrayOutputStream()
        ObjectOutputStream(bos).use {
            it.writeObject(this)
        }
        return bos.toByteArray()
    }

    fun loadFromByteArray(src: ByteArray) {
        ObjectInputStream(ByteArrayInputStream(src)).use {
            data.putAll((it.readObject() as FubukiPDC).data)
        }
    }
}

private object FubukiPersistentDataAdapterContext : PersistentDataAdapterContext {
    override fun newPersistentDataContainer(): PersistentDataContainer = FubukiPDC()
}