package moe.skjsjhb.mc.fubuki.schedule

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * An executor that runs its tasks on the server thread.
 *
 * Committed tasks will be run on the next server tick.
 * Any subsequent tasks that gets added by the tasks will be run on the next tick.
 */
class ServerThreadExecutor(private val nativeServer: MinecraftServer) : ExecutorService {
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
    private val shutdownFuture = CompletableFuture<Unit>()
    private val requestClose = AtomicBoolean(false)
    private val stopped = AtomicBoolean(false)

    init {
        executorMap[nativeServer] = this
    }

    private fun markClosed() {
        unregister()
        shutdownFuture.complete(Unit)
        stopped.set(true)
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

        if (taskQueue.isEmpty() && requestClose.get()) {
            markClosed()
        }
    }

    private fun unregister() {
        executorMap.remove(nativeServer)
    }

    override fun execute(command: Runnable) {
        if (requestClose.get()) return
        lastAddedTask.set(command)
        taskQueue.offer(command)
    }

    override fun shutdown() {
        requestClose.set(true)
    }

    override fun shutdownNow(): MutableList<Runnable> {
        markClosed()
        return taskQueue.toMutableList()
    }

    override fun isShutdown(): Boolean {
        return isTerminated || requestClose.get()
    }

    override fun isTerminated(): Boolean {
        return stopped.get()
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean =
        runCatching {
            shutdownFuture.get(timeout, unit)
        }.isSuccess

    override fun <T : Any?> submit(task: Callable<T>): Future<T> {
        val ft = CompletableFuture<T>()
        execute {
            runCatching {
                task.call()
            }.onSuccess {
                ft.complete(it)
            }.onFailure {
                ft.completeExceptionally(it)
            }
        }

        return ft
    }

    override fun <T : Any?> submit(task: Runnable, result: T): Future<T> {
        val ft = CompletableFuture<T>()
        execute {
            runCatching {
                task.run()
            }.onSuccess {
                ft.complete(result)
            }.onFailure {
                ft.completeExceptionally(it)
            }
        }

        return ft
    }

    override fun submit(task: Runnable): Future<*> {
        val ft = CompletableFuture<Unit>()
        execute {
            runCatching {
                task.run()
            }.onSuccess {
                ft.complete(Unit)
            }.onFailure {
                ft.completeExceptionally(it)
            }
        }

        return ft
    }

    override fun <T : Any?> invokeAll(tasks: MutableCollection<out Callable<T>>): MutableList<Future<T>> =
        tasks.map { submit(it) }.toMutableList()

    override fun <T : Any?> invokeAll(
        tasks: MutableCollection<out Callable<T>>,
        timeout: Long,
        unit: TimeUnit
    ): MutableList<Future<T>> {
        throw NotImplementedError("Task cancellation is not implemented on server thread yet")
    }

    override fun <T : Any> invokeAny(tasks: MutableCollection<out Callable<T>>): T {
        throw NotImplementedError("Task cancellation is not implemented on server thread yet")
    }

    override fun <T : Any> invokeAny(tasks: MutableCollection<out Callable<T>>, timeout: Long, unit: TimeUnit): T {
        throw NotImplementedError("Task cancellation is not implemented on server thread yet")
    }
}