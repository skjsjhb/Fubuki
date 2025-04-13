package moe.skjsjhb.mc.fubuki.server

import java.util.concurrent.CompletableFuture

/**
 * Receivers for various internal calls fired inside the native server.
 */
object ServerMixinReceivers {
    /**
     * Completed when all worlds are loaded.
     */
    val postWorldFuture = CompletableFuture<Any?>()
}