package moe.skjsjhb.mc.fubuki.ipc

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.plugin.messaging.PluginMessageListener
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration

class FubukiMessenger : Messenger {
    override fun isReservedChannel(channel: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun registerOutgoingPluginChannel(plugin: Plugin, channel: String) {
        TODO("Not yet implemented")
    }

    override fun unregisterOutgoingPluginChannel(plugin: Plugin, channel: String) {
        // TODO unregister plugin channel
    }

    override fun unregisterOutgoingPluginChannel(plugin: Plugin) {
        // TODO unregister plugin channel
    }

    override fun registerIncomingPluginChannel(
        plugin: Plugin,
        channel: String,
        listener: PluginMessageListener
    ): PluginMessageListenerRegistration {
        TODO("Not yet implemented")
    }

    override fun unregisterIncomingPluginChannel(plugin: Plugin, channel: String, listener: PluginMessageListener) {
        // TODO unregister plugin channel
    }

    override fun unregisterIncomingPluginChannel(plugin: Plugin, channel: String) {
        // TODO unregister plugin channel
    }

    override fun unregisterIncomingPluginChannel(plugin: Plugin) {
        // TODO unregister plugin channel
    }

    override fun getOutgoingChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getOutgoingChannels(plugin: Plugin): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getIncomingChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getIncomingChannels(plugin: Plugin): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getIncomingChannelRegistrations(plugin: Plugin): MutableSet<PluginMessageListenerRegistration> {
        TODO("Not yet implemented")
    }

    override fun getIncomingChannelRegistrations(channel: String): MutableSet<PluginMessageListenerRegistration> {
        TODO("Not yet implemented")
    }

    override fun getIncomingChannelRegistrations(
        plugin: Plugin,
        channel: String
    ): MutableSet<PluginMessageListenerRegistration> {
        TODO("Not yet implemented")
    }

    override fun isRegistrationValid(registration: PluginMessageListenerRegistration): Boolean {
        TODO("Not yet implemented")
    }

    override fun isIncomingChannelRegistered(plugin: Plugin, channel: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isOutgoingChannelRegistered(plugin: Plugin, channel: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchIncomingMessage(source: Player, channel: String, message: ByteArray) {
        TODO("Not yet implemented")
    }
}