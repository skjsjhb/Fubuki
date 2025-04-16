package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.entity.FubukiEntity
import org.bukkit.ServerTickManager
import org.bukkit.entity.Entity

class FubukiServerTickManager(private val delegate: net.minecraft.server.ServerTickManager) : ServerTickManager {
    override fun isRunningNormally(): Boolean = !delegate.isFrozen

    override fun isStepping(): Boolean =
        delegate.isStepping

    override fun isSprinting(): Boolean = delegate.isSprinting

    override fun isFrozen(): Boolean = delegate.isFrozen

    override fun isFrozen(entity: Entity): Boolean =
        delegate.shouldSkipTick((entity as FubukiEntity).toMojang())

    override fun setFrozen(frozen: Boolean) {
        delegate.isFrozen = frozen
    }

    override fun getTickRate(): Float =
        delegate.tickRate

    override fun setTickRate(tick: Float) {
        delegate.tickRate = tick
    }

    override fun stepGameIfFrozen(ticks: Int): Boolean =
        delegate.step(ticks)    // Frozen check is included

    override fun stopStepping(): Boolean =
        delegate.stopStepping()

    override fun requestGameToSprint(ticks: Int): Boolean =
        delegate.startSprint(ticks)

    override fun stopSprinting(): Boolean =
        delegate.stopSprinting()

    override fun getFrozenTicksToRun(): Int =
        delegate.stepTicks
}