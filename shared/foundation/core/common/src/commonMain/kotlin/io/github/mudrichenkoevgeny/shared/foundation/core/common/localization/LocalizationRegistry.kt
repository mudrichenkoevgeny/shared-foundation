package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.collection.createConcurrentMap

/**
 * Thread-safe registry holding module localization bundle providers with O(1) lookup.
 */
object LocalizationRegistry : LocalizationBundleProvider {

    private val providers: MutableMap<String, LocalizationBundleProvider> = createConcurrentMap()

    /**
     * Registers a [LocalizationBundleProvider] for the specified module path.
     */
    fun register(module: String, provider: LocalizationBundleProvider) {
        providers[module] = provider
    }

    override fun get(module: String, file: String, locale: String): Map<String, String>? {
        return providers[module]?.get(module, file, locale)
    }
}
