package moe.skjsjhb.mc.fubuki.schedule

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class FubukiTask(
    private val command: Runnable,
    private val plugin: Plugin,
    private val scheduler: FubukiScheduler,
    private val isSync: Boolean,
    private val delay: Int = -1,
    private val interval: Int = -1
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

    val nextTimeToRun = AtomicInteger(if (delay > 0) scheduler.getTicks() + delay else 0)

    override fun run() {
        if (isCancelled.get()) return

        isRunning.set(true)
        try {
            command.run()

            if (interval > 0) {
                nextTimeToRun.set(nextTimeToRun.get() + interval)
                scheduler.addTask(this)
            }
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
}