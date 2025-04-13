package moe.skjsjhb.mc.fubuki.server

import net.minecraft.server.MinecraftServer
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scheduler.BukkitWorker
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.function.Consumer

class FubukiScheduler(private val server: MinecraftServer) : BukkitScheduler {
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
        TODO("Not yet implemented")
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