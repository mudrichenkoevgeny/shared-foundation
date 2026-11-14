package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

/**
 * Extracts a non-blank single string parameter value for the specified [key].
 *
 * @param key Query parameter key to look up.
 * @return First non-blank string value if present, or `null`.
 */
public fun Map<String, List<String>>.parseNonBlankString(key: String): String? =
    this[key]?.firstOrNull()?.takeIf { it.isNotBlank() }

/**
 * Extracts a strictly parsed boolean parameter value for the specified [key].
 *
 * @param key Query parameter key to look up.
 * @return `true` or `false` if strictly parsed, or `null`.
 */
public fun Map<String, List<String>>.parseBoolean(key: String): Boolean? =
    this[key]?.firstOrNull()?.toBooleanStrictOrNull()

/**
 * Extracts a parsed [Int] parameter value for the specified [key].
 *
 * @param key Query parameter key to look up.
 * @return Parsed [Int] if valid, or `null`.
 */
public fun Map<String, List<String>>.parseInt(key: String): Int? =
    this[key]?.firstOrNull()?.toIntOrNull()

/**
 * Extracts a parsed [Long] parameter value for the specified [key].
 *
 * @param key Query parameter key to look up.
 * @return Parsed [Long] if valid, or `null`.
 */
public fun Map<String, List<String>>.parseLong(key: String): Long? =
    this[key]?.firstOrNull()?.toLongOrNull()

/**
 * Extracts a list of non-blank string parameter values for the specified [key].
 *
 * @param key Query parameter key to look up.
 * @return List of non-blank string values, or an empty list if absent.
 */
public fun Map<String, List<String>>.parseStringList(key: String): List<String> =
    this[key]?.filter { it.isNotBlank() } ?: emptyList()

/**
 * Extracts a list of enum parameter values for the specified [key] using [transform].
 *
 * @param key Query parameter key to look up.
 * @param transform Mapping function converting string values to enum instances or `null`.
 * @return List of parsed enum values, or an empty list if absent.
 */
public fun <E> Map<String, List<String>>.parseEnumList(
    key: String,
    transform: (String) -> E?
): List<E> = this[key]?.mapNotNull(transform) ?: emptyList()
