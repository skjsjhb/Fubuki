package moe.skjsjhb.mc.fubuki.schedule

import net.minecraft.server.MinecraftServer
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scheduler.BukkitWorker
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Future
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

@Suppress("OVERRIDE_DEPRECATION")
class FubukiScheduler(nativeServer: MinecraftServer) : BukkitScheduler {
    private val serverExecutor = ServerThreadExecutor(nativeServer)
    private val ticks = AtomicInteger(0)
    private val tasksQueue = PriorityBlockingQueue<FubukiTask>()

    private val tasksIds = ConcurrentHashMap<Int, FubukiTask>()
    private val pluginTasks = ConcurrentHashMap<Plugin, MutableSet<FubukiTask>>()

    init {
        scheduleTickCounter()
        scheduleTaskPoller()
    }

    /**
     * Gets the current tick of the server.
     */
    fun getTicks(): Int = ticks.get()

    /**
     * Adds an auto-renewal core task to count server ticks.
     */
    private fun scheduleTickCounter() {
        val tickCounter = object : Runnable {
            override fun run() {
                ticks.incrementAndGet()
                serverExecutor.execute(this)
            }
        }

        serverExecutor.execute(tickCounter)
    }

    /**
     * Adds an auto-renewal core task to poll the task queue.
     */
    private fun scheduleTaskPoller() {
        val taskPoller = object : Runnable {
            override fun run() {
                while (!tasksQueue.isEmpty()) {
                    val task = tasksQueue.peek() ?: break
                    if (task.nextTimeToRun.get() <= ticks.get()) {
                        tasksQueue.poll()
                        task.run()
                        if (task.possiblyRenew()) {
                            tasksQueue.offer(task)
                        } else {
                            deleteTask(task)
                        }
                    } else {
                        break
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

    override fun scheduleAsyncDelayedTask(plugin: Plugin, task: Runnable, delay: Long): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleAsyncDelayedTask(plugin: Plugin, task: Runnable): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleAsyncRepeatingTask(plugin: Plugin, task: Runnable, delay: Long, period: Long): Int {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> callSyncMethod(plugin: Plugin, task: Callable<T>): Future<T> {
        TODO("Not yet implemented")
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

    override fun runTaskAsynchronously(plugin: Plugin, task: Runnable): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>) {
        TODO("Not yet implemented")
    }

    override fun runTaskAsynchronously(plugin: Plugin, task: BukkitRunnable): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLater(plugin: Plugin, task: Runnable, delay: Long): BukkitTask =
        runTaskTimer(plugin, task, delay, -1)

    override fun runTaskLater(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) =
        runTaskTimer(plugin, task, delay, -1)

    override fun runTaskLater(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask =
        runTaskLater(plugin, task as Runnable, delay)

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Runnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) {
        TODO("Not yet implemented")
    }

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskTimer(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask =
        FubukiTask(task, plugin, this, true, delay, period).also { addTask(it) }

    override fun runTaskTimer(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long, period: Long) {
        var ts: FubukiTask? = null
        ts = FubukiTask({ task.accept(ts!!) }, plugin, this, true, delay, period).also { addTask(it) }
    }

    override fun runTaskTimer(plugin: Plugin, task: BukkitRunnable, delay: Long, period: Long): BukkitTask =
        runTaskTimer(plugin, task as Runnable, delay, period)

    override fun runTaskTimerAsynchronously(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskTimerAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long, period: Long) {
        TODO("Not yet implemented")
    }

    override fun runTaskTimerAsynchronously(
        plugin: Plugin,
        task: BukkitRunnable,
        delay: Long,
        period: Long
    ): BukkitTask {
        TODO("Not yet implemented")
    }

}