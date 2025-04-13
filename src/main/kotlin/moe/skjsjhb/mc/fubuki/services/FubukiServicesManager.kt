package moe.skjsjhb.mc.fubuki.services

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager

class FubukiServicesManager : ServicesManager {
    override fun <T : Any?> register(service: Class<T>, provider: T & Any, plugin: Plugin, priority: ServicePriority) {
        TODO("Not yet implemented")
    }

    override fun unregisterAll(plugin: Plugin) {
        // TODO unregister services
    }

    override fun unregister(service: Class<*>, provider: Any) {
        TODO("Not yet implemented")
    }

    override fun unregister(provider: Any) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> load(service: Class<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getRegistration(service: Class<T>): RegisteredServiceProvider<T>? {
        TODO("Not yet implemented")
    }

    override fun getRegistrations(plugin: Plugin): MutableList<RegisteredServiceProvider<*>> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getRegistrations(service: Class<T>): MutableCollection<RegisteredServiceProvider<T>> {
        TODO("Not yet implemented")
    }

    override fun getKnownServices(): MutableCollection<Class<*>> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> isProvidedFor(service: Class<T>): Boolean {
        TODO("Not yet implemented")
    }
}