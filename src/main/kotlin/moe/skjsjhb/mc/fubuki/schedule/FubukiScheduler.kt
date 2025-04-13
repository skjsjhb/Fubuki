package moe.skjsjhb.mc.fubuki.schedule

import net.minecraft.server.MinecraftServer
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scheduler.BukkitWorker
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

@Suppress("OVERRIDE_DEPRECATION")
class FubukiScheduler(nativeServer: MinecraftServer) : BukkitScheduler {
    private val serverExecutor = ServerThreadExecutor(nativeServer)
    private val ticks = AtomicInteger(0)
    private val tasksQueue = PriorityBlockingQueue<FubukiTask>()

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
                    val task = tasksQueue.poll() ?: break
                    if (task.nextTimeToRun.get() <= ticks.get()) {
                        task.run()
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
    internal fun addTask(task: FubukiTask) {
        tasksQueue.offer(task)
    }

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: Runnable, delay: Long): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: BukkitRunnable, delay: Long): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: Runnable): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleSyncDelayedTask(plugin: Plugin, task: BukkitRunnable): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleSyncRepeatingTask(plugin: Plugin, task: Runnable, delay: Long, period: Long): Int {
        TODO("Not yet implemented")
    }

    override fun scheduleSyncRepeatingTask(plugin: Plugin, task: BukkitRunnable, delay: Long, period: Long): Int {
        TODO("Not yet implemented")
    }

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
        TODO("Not yet implemented")
    }

    override fun cancelTasks(plugin: Plugin) {
        // TODO cancel tasks
    }

    override fun isCurrentlyRunning(taskId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun isQueued(taskId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getActiveWorkers(): MutableList<BukkitWorker> {
        TODO("Not yet implemented")
    }

    override fun getPendingTasks(): MutableList<BukkitTask> {
        TODO("Not yet implemented")
    }

    override fun runTask(plugin: Plugin, task: Runnable): BukkitTask {
        val ts = FubukiTask(task, plugin, this, true)
        addTask(ts)
        return ts
        // TODO associate task with plugin
    }

    override fun runTask(plugin: Plugin, task: Consumer<in BukkitTask>) {
        TODO("Not yet implemented")
    }

    override fun runTask(plugin: Plugin, task: BukkitRunnable): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskAsynchronously(plugin: Plugin, task: Runnable): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>) {
        TODO("Not yet implemented")
    }

    override fun runTaskAsynchronously(plugin: Plugin, task: BukkitRunnable): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLater(plugin: Plugin, task: Runnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLater(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) {
        TODO("Not yet implemented")
    }

    override fun runTaskLater(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Runnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long) {
        TODO("Not yet implemented")
    }

    override fun runTaskLaterAsynchronously(plugin: Plugin, task: BukkitRunnable, delay: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskTimer(plugin: Plugin, task: Runnable, delay: Long, period: Long): BukkitTask {
        TODO("Not yet implemented")
    }

    override fun runTaskTimer(plugin: Plugin, task: Consumer<in BukkitTask>, delay: Long, period: Long) {
        TODO("Not yet implemented")
    }

    override fun runTaskTimer(plugin: Plugin, task: BukkitRunnable, delay: Long, period: Long): BukkitTask {
        TODO("Not yet implemented")
    }

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