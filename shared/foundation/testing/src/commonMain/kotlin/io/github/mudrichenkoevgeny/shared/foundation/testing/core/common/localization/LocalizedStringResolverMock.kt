package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.LocalizedStringResolver

/**
 * Predictable mock implementation of [LocalizedStringResolver] for testing.
 */
class LocalizedStringResolverMock(
    private val prefix: String = "[Test]",
    private val nullKey: String? = null
) : LocalizedStringResolver {
    override fun resolve(module: String, file: String, key: String, locale: String): String? {
        if (key == nullKey) return null
        return "$prefix $locale:$module:$file:$key"
    }
}
