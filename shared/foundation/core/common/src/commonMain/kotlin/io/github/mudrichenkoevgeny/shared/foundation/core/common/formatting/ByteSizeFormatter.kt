package io.github.mudrichenkoevgeny.shared.foundation.core.common.formatting

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonLocales
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonModuleStrings
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.DefaultLocalizedStringResolver
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.LocalizedStringResolver

/**
 * Utility object for formatting raw byte numbers into localized human-readable byte size strings
 * (e.g. `100 B`, `500 KB`, `12 MB`, `1 GB`, `2 TB`).
 */
object ByteSizeFormatter {

    private const val ONE_KB = 1024L
    private const val ONE_MB = 1024L * 1024L
    private const val ONE_GB = 1024L * 1024L * 1024L
    private const val ONE_TB = 1024L * 1024L * 1024L * 1024L

    /**
     * Formats [bytesRaw] into a localized byte string using [resolver] and [locale].
     *
     * @param bytesRaw Raw number of bytes or string representation of bytes.
     * @param resolver Localized string resolver instance.
     * @param locale BCP 47 language code (defaults to [CommonLocales.DEFAULT]).
     * @return Localized byte size string (e.g. `"50 MB"`, `"1 GB"`), or raw string if parsing fails.
     */
    fun format(
        bytesRaw: Any?,
        resolver: LocalizedStringResolver = DefaultLocalizedStringResolver(),
        locale: String = CommonLocales.DEFAULT
    ): String? {
        val bytes = bytesRaw?.toString()?.toLongOrNull() ?: return bytesRaw?.toString()
        val strings = CommonModuleStrings(resolver, locale).general

        return when {
            bytes >= ONE_TB -> "${bytes / ONE_TB} ${strings.byteUnitTb}"
            bytes >= ONE_GB -> "${bytes / ONE_GB} ${strings.byteUnitGb}"
            bytes >= ONE_MB -> "${bytes / ONE_MB} ${strings.byteUnitMb}"
            bytes >= ONE_KB -> "${bytes / ONE_KB} ${strings.byteUnitKb}"
            else -> "$bytes ${strings.byteUnitB}"
        }
    }
}
