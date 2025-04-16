package moe.skjsjhb.mc.fubuki.network

@Suppress("FunctionName")
interface HostnameContainer {
    fun `fubuki$setHostname`(hostname: String)
    fun `fubuki$getHostname`(): String
}