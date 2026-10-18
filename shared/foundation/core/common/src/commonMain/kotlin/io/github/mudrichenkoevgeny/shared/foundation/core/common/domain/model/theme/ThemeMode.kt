package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.theme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val MODE_LIGHT = "light"
private const val MODE_DARK = "dark"
private const val MODE_SYSTEM = "system"

/**
 * Represents application UI theme mode preference.
 *
 * @property value String representation of [ThemeMode].
 */
@Serializable
enum class ThemeMode(val value: String) {

    /**
     * Explicit light theme mode.
     */
    @SerialName(MODE_LIGHT)
    LIGHT(MODE_LIGHT),

    /**
     * Explicit dark theme mode.
     */
    @SerialName(MODE_DARK)
    DARK(MODE_DARK),

    /**
     * Follow system or OS theme settings.
     */
    @SerialName(MODE_SYSTEM)
    SYSTEM(MODE_SYSTEM);

    companion object {

        /**
         * Default theme mode preference ([SYSTEM]).
         */
        val DEFAULT: ThemeMode = SYSTEM

        /**
         * Returns [ThemeMode] for the given wire string or enum-style string, or null if value is unmapped.
         *
         * @param value Case-insensitive enum name (e.g. `LIGHT`) or a wire string such as [MODE_LIGHT].
         */
        fun fromValueOrNull(value: String?): ThemeMode? {
            if (value.isNullOrBlank()) return null
            return entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }
        }

        /**
         * Returns [ThemeMode] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [ThemeMode].
         */
        fun fromValueOrThrow(value: String): ThemeMode =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for ThemeMode: '$value'")
    }
}
