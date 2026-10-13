package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

/**
 * Utility for normalizing raw URL query parameters from various platform callers (such as JS/TS query objects).
 */
object QueryParamNormalizer {

    /**
     * Normalizes a raw query parameter map where values may be single strings, arrays, or lists into a canonical map of string lists.
     *
     * Handles single strings, lists, arrays, iterables, and null/blank values.
     *
     * @param params Raw query map where values can be [String], [List], [Array], or null/undefined.
     * @return Normalized map where every key points to a list of non-blank string values.
     */
    fun normalize(params: Map<String, Any?>): Map<String, List<String>> {
        return params.mapValues { (_, value) ->
            when (value) {
                null -> emptyList()
                is List<*> -> value.mapNotNull { it?.toString()?.takeIf { str -> str.isNotBlank() } }
                is Array<*> -> value.mapNotNull { it?.toString()?.takeIf { str -> str.isNotBlank() } }
                is Iterable<*> -> value.mapNotNull { it?.toString()?.takeIf { str -> str.isNotBlank() } }
                else -> {
                    val str = value.toString()
                    if (str.isNotBlank()) listOf(str) else emptyList()
                }
            }
        }
    }
}

/**
 * Extension function to normalize a raw query parameter map where values may be single strings, arrays, or lists.
 */
fun Map<String, Any?>.normalizeQueryParams(): Map<String, List<String>> {
    return QueryParamNormalizer.normalize(this)
}
