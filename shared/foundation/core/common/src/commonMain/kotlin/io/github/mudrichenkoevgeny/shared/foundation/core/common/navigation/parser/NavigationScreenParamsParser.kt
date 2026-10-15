package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams

/**
 * Contract for bidirectional serialization and deserialization of screen navigation parameters.
 *
 * @param T The concrete [NavigationScreenParams] type handled by this parser.
 */
interface NavigationScreenParamsParser<T : NavigationScreenParams<T>> {

    /**
     * Parses raw URL query or path parameters into typed parameter instance [T].
     *
     * @param params Map of parameter keys to raw parameter values.
     * @return Validated instance of [T] with fallback defaults applied.
     */
    fun parse(params: Map<String, Any?>): T

    /**
     * Serializes typed parameters [T] into a multi-value string map suitable for building URL query strings.
     *
     * @param params Typed parameter instance to serialize.
     * @return Map where keys represent query parameter names and values represent non-empty list of string values.
     */
    fun serialize(params: T): Map<String, List<String>>
}