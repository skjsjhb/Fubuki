package moe.skjsjhb.mc.fubuki.schedule

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class FubukiTask(
    private val command: Runnable,
    private val plugin: Plugin,
    private val scheduler: FubukiScheduler,
    private val isSync: Boolean,
    private val delay: Long = -1,
    private val interval: Long = -1
) : BukkitTask, Runnable, Comparable<FubukiTask> {
    companion object {
        private var nextId = 0

        @Synchronized
        fun getNewTaskId(): Int {
            return nextId++
        }
    }

    private val id = getNewTaskId()
    private val isCancelled = AtomicBoolean(false)
    private val isRunning = AtomicBoolean(false)

    val nextTimeToRun = AtomicLong(if (delay > 0) scheduler.getTicks() + delay else 0)

    /**
     * Renews the task, if applicable.
     *
     * The task will be added to the queue automatically if returns true, or get deleted from the scheduler otherwise.
     */
    fun possiblyRenew(): Boolean {
        if (isCancelled.get()) return false

        if (interval >= 0) {
            // If interval is 0 it runs on every tick
            nextTimeToRun.set(nextTimeToRun.get() + interval + 1)
            return true
        }

        return false
    }

    override fun run() {
        if (isCancelled.get()) return

        isRunning.set(true)
        try {
            command.run()
        } finally {
            isRunning.set(false)
        }
    }

    override fun getTaskId(): Int = id

    override fun getOwner(): Plugin = plugin

    override fun isSync(): Boolean = isSync

    override fun isCancelled(): Boolean = isCancelled.get()

    override fun cancel() {
        isCancelled.set(true)
    }

    override fun compareTo(other: FubukiTask): Int = nextTimeToRun.get().compareTo(other.nextTimeToRun.get())

    /**
     * Checks if the task is currently running.
     */
    fun isRunning() = isRunning.get()
}