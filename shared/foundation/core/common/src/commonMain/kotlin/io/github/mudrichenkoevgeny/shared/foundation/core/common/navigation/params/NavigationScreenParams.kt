package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params

/**
 * Marker contract for strongly typed navigation screen parameter models (`*Params`).
 *
 * Provides a standardized method to serialize typed parameter objects back into a multi-value
 * parameter map suitable for building URL query strings, Deep Links, or GET API request parameters.
 */
interface NavigationScreenParams {

    /**
     * Serializes this screen parameter object into a map of key-to-value-list parameter entries.
     *
     * @return Map where keys represent parameter names and values represent non-empty list of string values.
     */
    fun toQueryMap(): Map<String, List<String>>
}
