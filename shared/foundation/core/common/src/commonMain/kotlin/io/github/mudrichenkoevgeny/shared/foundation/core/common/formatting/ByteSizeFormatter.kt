package io.github.mudrichenkoevgeny.shared.foundation.core.common.formatting

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonLocales
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonModuleStrings
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.DefaultLocalizedStringResolver
import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.LocalizedStringResolver
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * Utility object for formatting raw byte numbers into localized human-readable byte size strings
 * (e.g. `100 B`, `500 KB`, `12.5 MB`, `1.2 GB`, `2 TB`).
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
     * @param decimals Number of decimal places to show for KB and above (defaults to 0 for whole numbers).
     * @return Localized byte size string (e.g. `"50 MB"`, `"1.5 GB"`), or raw string if parsing fails.
     */
    fun format(
        bytesRaw: Any?,
        resolver: LocalizedStringResolver = DefaultLocalizedStringResolver(),
        locale: String = CommonLocales.DEFAULT,
        decimals: Int = 0
    ): String? {
        if (bytesRaw == null) return null

        val bytes = parseBytes(bytesRaw) ?: return bytesRaw.toString()
        val strings = CommonModuleStrings(resolver, locale).general
        val absBytes = abs(bytes)

        if (absBytes < ONE_KB) {
            return "$bytes ${strings.byteUnitB}"
        }

        return when {
            absBytes >= ONE_TB -> "${formatValue(bytes, ONE_TB, decimals)} ${strings.byteUnitTb}"
            absBytes >= ONE_GB -> "${formatValue(bytes, ONE_GB, decimals)} ${strings.byteUnitGb}"
            absBytes >= ONE_MB -> "${formatValue(bytes, ONE_MB, decimals)} ${strings.byteUnitMb}"
            else -> "${formatValue(bytes, ONE_KB, decimals)} ${strings.byteUnitKb}"
        }
    }

    private fun parseBytes(raw: Any): Long? {
        return when (raw) {
            is Number -> raw.toLong()
            is String -> raw.toLongOrNull() ?: raw.toDoubleOrNull()?.toLong()
            else -> raw.toString().toLongOrNull() ?: raw.toString().toDoubleOrNull()?.toLong()
        }
    }

    private fun formatValue(bytes: Long, divisor: Long, decimals: Int): String {
        if (decimals <= 0) {
            return (bytes / divisor).toString()
        }

        val value = bytes.toDouble() / divisor
        val factor = 10.0.pow(decimals)
        val roundedValue = (value * factor).roundToLong()
        val integerPart = roundedValue / factor.toLong()
        val fractionalPart = abs(roundedValue % factor.toLong())

        if (fractionalPart == 0L) {
            return integerPart.toString()
        }

        val fractionalStr = fractionalPart.toString()
            .padStart(decimals, '0')
            .trimEnd('0')

        return if (fractionalStr.isEmpty()) {
            integerPart.toString()
        } else {
            "$integerPart.$fractionalStr"
        }
    }
}
