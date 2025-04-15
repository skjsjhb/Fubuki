package moe.skjsjhb.mc.fubuki.interop

import net.fabricmc.loader.api.FabricLoader

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

fun Any.asBukkitRef(): BukkitRef {
    if (this !is BukkitRef) {
        throw UnsupportedOperationException(
            "This class ${this::class.java.name} is not capable for ref (is the mixin missing?)"
        )
    }
    return this
}

/**
 * Gets the Bukkit ref of this object, creates/replaces the value if not bounded or type mismatch.
 */
inline fun <T : Any, reified E : Any> T.asBukkit(factory: (T) -> E): E {
    asBukkitRef().let {
        val v = it.`fubuki$getRef`()
        if (v is E) return v

        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            if (v != null && !v::class.java.isAssignableFrom(E::class.java)) {
                throw UnsupportedOperationException(
                    "A value of type ${E::class.java.name} tries to replace existing value of unrelated type ${v::class.java.name} (are multiple refs assigned?)"
                )
            }
        }

        val fv = factory(this)
        it.`fubuki$setRef`(fv)
        return fv
    }
}

/**
 * Gets the Bukkit ref of this object (assuming it exists).
 *
 * The behavior is undefined if invoked for an object that does not contain such ref.
 */
@Suppress("UNCHECKED_CAST")
fun <E> Any.assumeBukkit(): E = asBukkitRef().`fubuki$getRef`() as E