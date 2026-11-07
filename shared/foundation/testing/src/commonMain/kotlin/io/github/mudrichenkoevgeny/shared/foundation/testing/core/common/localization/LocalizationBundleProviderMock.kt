package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.LocalizationBundleProvider

/**
 * In-memory mock implementation of [LocalizationBundleProvider] for testing.
 *
 * @param bundles Map where the key is formatted as "$module/$file/$locale"
 *                and the value is the key-value map of translations.
 */
public class LocalizationBundleProviderMock(
    private val bundles: Map<String, Map<String, String>> = emptyMap()
) : LocalizationBundleProvider {
    public override fun get(
        module: String,
        file: String,
        locale: String
    ): Map<String, String>? {
        val key = buildKey(module, file, locale)
        return bundles[key]
    }

    public companion object {
        public fun buildKey(
            module: String,
            file: String,
            locale: String
        ): String = "$module/$file/$locale"
    }
}
