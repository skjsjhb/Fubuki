package moe.skjsjhb.mc.fubuki.schedule

import net.minecraft.server.MinecraftServer
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scheduler.BukkitWorker
import java.util.*
import java.util.concurrent.*
import java.util.function.Consumer

@Suppress("OVERRIDE_DEPRECATION")
class FubukiScheduler(private val nativeServer: MinecraftServer) : BukkitScheduler {
    private val serverExecutor = ServerThreadExecutor(nativeServer)
    private val tasksQueue = PriorityBlockingQueue<FubukiTask>()

    private val tasksIds = ConcurrentHashMap<Int, FubukiTask>()
    private val pluginTasks = ConcurrentHashMap<Plugin, MutableSet<FubukiTask>>()

    init {
        scheduleTaskPoller()
    }

    /**
     * Gets the current tick of the server.
     */
    fun getTicks(): Int = nativeServer.ticks

    /**
     * Adds an auto-renewal core task to poll the task queue.
     */
    private fun scheduleTaskPoller() {
        val taskPoller = object : Runnable {
            override fun run() {
                while (!tasksQueue.isEmpty()) {
                    val task = tasksQueue.peek() ?: break
                    if (task.nextTimeToRun.get() > getTicks()) break

                    tasksQueue.poll()
                    task.run()

                    if (task.possiblyRenew()) {
                        tasksQueue.offer(task)
                    } else {
                        deleteTask(task)
                    }
                }
                
                serverExecutor.execute(this)
            }
        }

        serverExecutor.execute(taskPoller)
    }

    /**
     * Adds a task to the queue.
     */
    private fun addTask(task: FubukiTask) {
        tasksQueue.offer(task)
        pluginTasks.computeIfAbsent(task.owner) { Collections.synchronizedSet(HashSet()) }.add(task)
        tasksIds[task.taskId] = task
    }

    /**
     * Deletes a task.
     */
    private fun deleteTask(task: FubukiTask) {
        tasksIds.remove(task.taskId)
        pluginTasks[task.owner]?.remove(task)
    }

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: Runnable, delay: Long): Int =
        runTaskLater(plugin, task, delay).taskId

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: BukkitRunnable, delay: Long): Int =
        runTaskLater(plugin, task, delay).taskId

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: Runnable): Int =
        runTask(plugin, task).taskId

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: BukkitRunnable): Int =
        runTask(plugin, task).taskId

    override fun scheduleSyncRepeatingTask(plugin: Plugin, task: Runnable, delay: Long, period: Long): Int =
        runTaskTimer(plugin, task, delay, period).taskId

    override fun scheduleSyncRepeatingTask(plugin: Plugin, task: BukkitRunnable, delay: Long, period: Long): Int =
        runTaskTimer(plugin, task, delay, period).taskId

    override fun scheduleAsyncDelayedTask(plugin: Plugin, task: Runnable, delay: Long): Int =
        runTaskLaterAsynchronously(plugin, task, delay).taskId

    override fun scheduleAsyncDelayedTask(plugin: Plugin, task: Runnable): Int =
        scheduleAsyncDelayedTask(plugin, task, -1)

    override fun scheduleAsyncRepeatingTask(plugin: Plugin, task: Runnable, delay: Long, period: Long): Int =
        runTaskTimerAsynchronously(plugin, task, delay, period).taskId

    fun runOnMainThread(fn: () -> Unit) {
        serverExecutor.execute(fn)
    }

    override fun <T : Any?> callSyncMethod(plugin: Plugin, task: Callable<T>): Future<T> {
        val ft = CompletableFuture<T>()
        serverExecutor.execute {
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

    override fun cancelTask(taskId: Int) {
        tasksIds[taskId]?.cancel()
    }

    override fun cancelTasks(plugin: Plugin) {
        pluginTasks[plugin]?.forEach { it.cancel() }
    }

    override fun isCurrentlyRunning(taskId: Int): Boolean =
        tasksIds[taskId]?.isRunning() == true

    override fun isQueued(taskId: Int): Boolean =
        tasksIds.contains(taskId) && !isCurrentlyRunning(taskId)

    override fun getActiveWorkers(): MutableList<BukkitWorker> {
        TODO("Not yet implemented")
    }

    override fun getPendingTasks(): MutableList<BukkitTask> =
        tasksQueue.toMutableList()

    override fun runTask(plugin: Plugin, task: Runnable): BukkitTask =
        runTaskLater(plugin, task, -1)

    override fun runTask(plugin: Plugin, task: Consumer<in BukkitTask>) =
        runTaskLater(plugin, task, -1)

    override fun runTask(plugin: Plugin, task: BukkitRunnable): BukkitTask =
        runTask(plugin, task as Runnable)

    override fun runTaskAsynchronously(plugin: Plugin, task: Runnable): BukkitTask =
        runTaskLaterAsynchronously(plugin, task, -1)

    override fun runTaskAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>) =
        runTaskLaterAsynchronously(plugin, task, -1)

    override fun runTaskAsynchronously(plugin: Plugin, task: BukkitRunnable): BukkitTask =
        runTaskAsynchronously(plugin, task as Runnable)

    override fun runTaskLater(plugin: Plugin, task: Runnable, delay: Long): BukkitTask =
        runTaskTimer(plugin, task, delay, -1)

    override fun runTaskLater(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) =
        runTaskTimer(plugin, task, delay, -1)

    override fun runTaskLater(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask =
        runTaskLater(plugin, task as Runnable, delay)

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Runnable, delay: Long): BukkitTask =
        runTaskTimerAsynchronously(plugin, task, delay, -1)

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) =
        runTaskTimerAsynchronously(plugin, task, delay, -1)

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask =
        runTaskLaterAsynchronously(plugin, task as Runnable, delay)

    override fun runTaskTimer(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask =
        FubukiTask(task, plugin, this, true, delay, period).also { addTask(it) }

    override fun runTaskTimer(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long, period: Long) {
        var ts: FubukiTask? = null
        ts = FubukiTask({ task.accept(ts!!) }, plugin, this, true, delay, period).also { addTask(it) }
    }

    override fun runTaskTimer(plugin: Plugin, task: BukkitRunnable, delay: Long, period: Long): BukkitTask =
        runTaskTimer(plugin, task as Runnable, delay, period)

    override fun runTaskTimerAsynchronously(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask =
        FubukiTask(task, plugin, this, false, delay, period).also { addTask(it) }

    override fun runTaskTimerAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long, period: Long) {
        var ts: FubukiTask? = null
        ts = FubukiTask({ task.accept(ts!!) }, plugin, this, false, delay, period).also { addTask(it) }
    }

    override fun runTaskTimerAsynchronously(
        plugin: Plugin,
        task: BukkitRunnable,
        delay: Long,
        period: Long
    ): BukkitTask = runTaskTimerAsynchronously(plugin, task as Runnable, delay, period)

}