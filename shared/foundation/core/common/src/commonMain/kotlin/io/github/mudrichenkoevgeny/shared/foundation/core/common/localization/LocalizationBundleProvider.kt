package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

/**
 * Functional provider supplying localization bundle maps for a given module, file, and locale.
 */
fun interface LocalizationBundleProvider {

    /**
     * Retrieves string key-value map for specified bundle module, file name, and locale code.
     *
     * @param module Relative module identifier (e.g., "core/common", "feature/user").
     * @param file Bundle file name without extension (e.g., "errors", "ui").
     * @param locale Target locale code (e.g., [CommonLocales.ENGLISH], [CommonLocales.RUSSIAN], "en-US").
     * @return Localized string map if found, or null otherwise.
     */
    fun get(module: String, file: String, locale: String): Map<String, String>?
}
