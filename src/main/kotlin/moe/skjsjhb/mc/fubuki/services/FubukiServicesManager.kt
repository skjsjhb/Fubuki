package moe.skjsjhb.mc.fubuki.services

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.PriorityBlockingQueue

class FubukiServicesManager : ServicesManager {
    private val services = ConcurrentHashMap<Class<*>, PriorityBlockingQueue<RegisteredServiceProvider<*>>>()

    override fun <T : Any> register(service: Class<T>, provider: T, plugin: Plugin, priority: ServicePriority) {
        val rsp = RegisteredServiceProvider(service, provider, priority, plugin)
        services.getOrPut(service) { PriorityBlockingQueue() }.add(rsp)
    }

    override fun unregisterAll(plugin: Plugin) {
        services.values.forEach {
            it.removeIf { it.plugin == plugin }
        }
    }

    override fun unregister(service: Class<*>, provider: Any) {
        services[service]?.removeIf { it.provider == provider }
    }

    override fun unregister(provider: Any) {
        services.values.forEach {
            it.removeIf { it.provider == provider }
        }
    }

    override fun <T : Any> load(service: Class<T>): T? = getRegistration(service)?.provider

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getRegistration(service: Class<T>): RegisteredServiceProvider<T>? =
        services[service]?.peek() as? RegisteredServiceProvider<T>?

    override fun getRegistrations(plugin: Plugin): MutableList<RegisteredServiceProvider<*>> =
        services.values.flatMap { it.filter { it.plugin == plugin } }.toMutableList()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getRegistrations(service: Class<T>): MutableCollection<RegisteredServiceProvider<T>> =
        services[service] as? MutableCollection<RegisteredServiceProvider<T>> ?: mutableListOf()

    override fun getKnownServices(): MutableCollection<Class<*>> =
        services.filter { it.value.isNotEmpty() }.map { it.key }.toMutableList()

    override fun <T : Any> isProvidedFor(service: Class<T>): Boolean =
        services[service]?.isNotEmpty() == true
}