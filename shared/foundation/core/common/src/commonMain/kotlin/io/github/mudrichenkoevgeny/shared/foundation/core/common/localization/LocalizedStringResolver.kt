package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

/**
 * Contract for resolving localized string values by module, bundle file, key, and locale.
 */
fun interface LocalizedStringResolver {

    /**
     * Resolves a localized template string.
     *
     * @param module Relative module identifier (e.g., "core/common", "feature/user").
     * @param file Bundle file name without extension (e.g., "errors", "ui").
     * @param key String key defined in the localization bundle.
     * @param locale Desired target locale code (e.g., [CommonLocales.ENGLISH], [CommonLocales.RUSSIAN], "en-US").
     * @return Localized string template if found, or null otherwise.
     */
    fun resolve(module: String, file: String, key: String, locale: String): String?
}