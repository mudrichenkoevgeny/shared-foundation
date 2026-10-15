package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser

/**
 * Marker contract for strongly typed navigation screen parameter models.
 *
 * Enforces parser companion implementation and provides standardized serialization back into
 * a multi-value parameter map for building URL query strings, Deep Links, or GET API requests.
 *
 * @param T The concrete parameter type implementing this interface.
 */
interface NavigationScreenParams<T : NavigationScreenParams<T>> {

    /**
     * Reference to the parser implementation for this parameter model type.
     */
    val parser: NavigationScreenParamsParser<T>

    /**
     * Serializes this screen parameter object into a map of key-to-value-list parameter entries.
     *
     * @return Map where keys represent parameter names and values represent non-empty list of string values.
     */
    @Suppress("UNCHECKED_CAST")
    fun toQueryMap(): Map<String, List<String>> =
        parser.serialize(this as T)
}