package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

/**
 * Normalizes a raw phone query parameter string, restoring a leading '+' if it was converted into a space ' '
 * during standard URL query decoding (e.g., `?phone=+1234567890` decoded as `" 1234567890"`).
 *
 * @receiver Raw phone query parameter string, which may be null, blank, or space-prefixed.
 * @return Cleaned phone string with leading '+' restored if space-prefixed before digits, or null if blank/null.
 */
fun String?.normalizePhoneQueryParam(): String? {
    if (this.isNullOrBlank()) return null
    val trimmed = this.trim()
    if (trimmed.isEmpty()) return null

    val restored = if (this.startsWith(' ') && !trimmed.startsWith('+') && trimmed.first().isDigit()) {
        "+$trimmed"
    } else {
        trimmed
    }
    return restored
}

/**
 * Normalizes a list of raw phone query parameter strings, restoring leading '+' if converted into spaces.
 *
 * @receiver List of raw phone query parameter strings, which may be null or empty.
 * @return List of cleaned phone strings with restored leading '+'.
 */
fun List<String>?.normalizePhoneQueryParams(): List<String> {
    if (this.isNullOrEmpty()) return emptyList()
    return this.mapNotNull { it.normalizePhoneQueryParam() }
}