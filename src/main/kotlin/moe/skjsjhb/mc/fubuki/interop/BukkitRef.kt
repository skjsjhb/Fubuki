package moe.skjsjhb.mc.fubuki.interop

/**
 * Defines a way to store and retrieve a custom object on vanilla objects.
 *
 * For internal use only.
 */
@Suppress("FunctionName")
interface BukkitRef {
    /**
     * Sets the Bukkit bindings for this object.
     */
    fun `fubuki$setRef`(obj: Any?)

    /**
     * Gets the Bukkit bindings for this object.
     */
    fun `fubuki$getRef`(): Any?
}

private fun <T : Any> T.asBukkitRef(): BukkitRef {
    if (this !is BukkitRef) {
        throw UnsupportedOperationException(
            "This class ${this::class.java.name} is not capable for ref (is the mixin missing?)"
        )
    }
    return this
}

@Suppress("UNCHECKED_CAST")
fun <E> Any.asBukkit(): E =
    asBukkitRef().`fubuki$getRef`()?.let { it as E } ?: throw IllegalStateException("No value set for this ref")

fun <E> Any.setBukkit(obj: E) {
    asBukkitRef().`fubuki$setRef`(obj)
}
