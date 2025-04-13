package moe.skjsjhb.mc.fubuki.server

import net.minecraft.server.MinecraftServer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

class ServerTickInjector(private val nativeServer: MinecraftServer) : AutoCloseable {
    companion object {
        private val notifiers = ConcurrentHashMap<MinecraftServer, ServerTickInjector>()

        /**
         * Executes when any server tick begins.
         *
         * Intended for uses from mixins only.
         */
        @JvmStatic
        fun onBeginTick(server: MinecraftServer) {
            notifiers[server]?.onSelfBeginTick()
        }
    }

    init {
        notifiers[nativeServer] = this
    }

    override fun close() {
        notifiers.remove(nativeServer)
    }

    /**
     * Active tasks to be added to the executor on the next tick.
     */
    private val activeTasks = ConcurrentLinkedQueue<Runnable>()

    /**
     * Ticks since last reset.
     */
    private var ticksSinceStart = 0

    /**
     * Resets the ticks count to 0.
     *
     * This method must be called before enabling all plugins to emulate the same timing feature in Bukkit.
     */
    fun resetTicks() {
        ticksSinceStart = 0
    }

    /**
     * Gets ticks passed since start.
     */
    fun getTicks(): Int = ticksSinceStart

    /**
     * Runs the specified task on the next tick.
     */
    fun runOnNextTick(task: Runnable) {
        activeTasks.offer(task)
    }

    /**
     * Executes when the tick starts to run on this server.
     *
     * Intended for uses from mixins only.
     */
    private fun onSelfBeginTick() {
        ticksSinceStart++
        activeTasks.forEach { it.run() }
    }
}