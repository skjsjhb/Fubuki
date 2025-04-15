package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.entity.FubukiPlayer
import moe.skjsjhb.mc.fubuki.entity.toBukkit
import net.minecraft.server.network.ServerPlayerEntity
import org.bukkit.entity.Player

/**
 * A real-time view of player list.
 *
 * The content of this class is not mutable, it implements [MutableCollection] for type compatibility.
 */
class PlayerListView(
    private val source: Collection<ServerPlayerEntity>
) : MutableCollection<Player> {
    override val size: Int
        get() = source.size

    override fun clear() {
        throw UnsupportedOperationException("Player list view cannot be modified")
    }

    override fun addAll(elements: Collection<Player>): Boolean {
        throw UnsupportedOperationException("Player list view cannot be modified")
    }

    override fun add(element: Player): Boolean {
        throw UnsupportedOperationException("Player list view cannot be modified")
    }

    override fun containsAll(elements: Collection<Player>): Boolean =
        source.containsAll(elements.map { (it as FubukiPlayer).toMojang() })

    override fun contains(element: Player): Boolean =
        source.contains((element as FubukiPlayer).toMojang())

    override fun isEmpty(): Boolean = source.isEmpty()

    override fun iterator(): MutableIterator<Player> =
        source.map { it.toBukkit() }.toMutableList().iterator()

    override fun retainAll(elements: Collection<Player>): Boolean {
        throw NotImplementedError("Player list view cannot be modified")
    }

    override fun removeAll(elements: Collection<Player>): Boolean {
        throw NotImplementedError("Player list view cannot be modified")
    }

    override fun remove(element: Player): Boolean {
        throw NotImplementedError("Player list view cannot be modified")
    }
}