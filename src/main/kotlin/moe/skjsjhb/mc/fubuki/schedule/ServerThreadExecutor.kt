package moe.skjsjhb.mc.fubuki.schedule

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicReference

/**
 * An executor that runs its tasks on the server thread.
 *
 * Committed tasks will be run on the next server tick.
 * Any subsequent tasks that gets added by the tasks will be run on the next tick.
 */
class ServerThreadExecutor(private val nativeServer: MinecraftServer) : Executor, AutoCloseable {
    companion object {
        private val executorMap = ConcurrentHashMap<MinecraftServer, ServerThreadExecutor>()

        fun init() {
            ServerTickEvents.START_SERVER_TICK.register { sv ->
                executorMap[sv]?.runTasks()
            }
        }
    }

    private val taskQueue = ConcurrentLinkedQueue<Runnable>()
    private val lastAddedTask = AtomicReference<Runnable?>(null)

    init {
        executorMap[nativeServer] = this
    }

    private fun runTasks() {
        var running = true
        val stopMark = lastAddedTask.get()
        while (running) {
            val t = taskQueue.poll() ?: break
            if (stopMark == t) {
                running = false
            }

            t.run()
        }
    }

    private fun unregister() {
        executorMap.remove(nativeServer)
    }

    override fun execute(command: Runnable) {
        lastAddedTask.set(command)
        taskQueue.offer(command)
    }

    override fun close() {
        unregister()
    }
}