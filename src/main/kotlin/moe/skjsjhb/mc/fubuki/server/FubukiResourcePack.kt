package moe.skjsjhb.mc.fubuki.server

import net.minecraft.server.MinecraftServer
import org.bukkit.packs.ResourcePack
import java.util.*

class FubukiResourcePack(private val source: MinecraftServer.ServerResourcePackProperties) : ResourcePack {
    override fun getId(): UUID = source.id

    override fun getUrl(): String = source.url

    override fun getHash(): String? = source.hash

    override fun getPrompt(): String? = source.prompt?.literalString

    override fun isRequired(): Boolean = source.isRequired
}