package moe.skjsjhb.mc.fubuki.entity

import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.EntityPose
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.math.Direction
import org.bukkit.block.BlockFace
import org.bukkit.block.PistonMoveReaction
import org.bukkit.entity.Pose
import org.bukkit.entity.SpawnCategory

fun PistonBehavior?.toBukkit(): PistonMoveReaction = when (this) {
    PistonBehavior.NORMAL -> PistonMoveReaction.MOVE
    PistonBehavior.DESTROY -> PistonMoveReaction.BREAK
    PistonBehavior.BLOCK -> PistonMoveReaction.BLOCK
    PistonBehavior.PUSH_ONLY -> PistonMoveReaction.PUSH_ONLY
    else -> PistonMoveReaction.IGNORE
}

fun Direction?.toBukkit(): BlockFace = when (this) {
    Direction.UP -> BlockFace.UP
    Direction.DOWN -> BlockFace.DOWN
    Direction.NORTH -> BlockFace.NORTH
    Direction.SOUTH -> BlockFace.SOUTH
    Direction.WEST -> BlockFace.WEST
    Direction.EAST -> BlockFace.EAST
    else -> BlockFace.SELF
}

fun EntityPose?.toBukkit(): Pose = when (this) {
    // CraftBukkit maps them by ordinal
    // We'll just use the values as-is :)
    EntityPose.STANDING -> Pose.STANDING
    EntityPose.GLIDING -> Pose.FALL_FLYING
    EntityPose.SLEEPING -> Pose.SLEEPING
    EntityPose.SWIMMING -> Pose.SWIMMING
    EntityPose.SPIN_ATTACK -> Pose.SPIN_ATTACK
    EntityPose.CROUCHING -> Pose.SNEAKING
    EntityPose.LONG_JUMPING -> Pose.LONG_JUMPING
    EntityPose.DYING -> Pose.DYING
    EntityPose.CROAKING -> Pose.CROAKING
    EntityPose.USING_TONGUE -> Pose.USING_TONGUE
    EntityPose.SITTING -> Pose.SITTING
    EntityPose.ROARING -> Pose.ROARING
    EntityPose.SNIFFING -> Pose.SNIFFING
    EntityPose.EMERGING -> Pose.EMERGING
    EntityPose.DIGGING -> Pose.DIGGING
    EntityPose.SLIDING -> Pose.SLIDING
    EntityPose.SHOOTING -> Pose.SHOOTING
    EntityPose.INHALING -> Pose.INHALING
    else -> Pose.STANDING
}

fun SpawnGroup?.toBukkit(): SpawnCategory = when (this) {
    SpawnGroup.MONSTER -> SpawnCategory.MONSTER
    SpawnGroup.CREATURE -> SpawnCategory.ANIMAL
    SpawnGroup.AMBIENT -> SpawnCategory.AMBIENT
    SpawnGroup.AXOLOTLS -> SpawnCategory.AXOLOTL
    SpawnGroup.UNDERGROUND_WATER_CREATURE -> SpawnCategory.WATER_UNDERGROUND_CREATURE
    SpawnGroup.WATER_CREATURE -> SpawnCategory.WATER_ANIMAL
    SpawnGroup.WATER_AMBIENT -> SpawnCategory.WATER_AMBIENT
    SpawnGroup.MISC -> SpawnCategory.MISC
    else -> SpawnCategory.MISC
}