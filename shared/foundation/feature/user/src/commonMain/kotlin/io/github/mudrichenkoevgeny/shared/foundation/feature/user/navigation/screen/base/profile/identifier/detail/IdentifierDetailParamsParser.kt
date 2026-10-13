package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Bidirectional parser and serializer for [IdentifierDetailParams] Deep Link and Web Router URL query and path parameters.
 */
object IdentifierDetailParamsParser {

    /**
     * Parses raw Deep Link or Web Router query/path parameters into typed [IdentifierDetailParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [IdentifierDetailParams] instance.
     */
    fun parse(params: Map<String, Any?>): IdentifierDetailParams {
        val normalized = params.normalizeQueryParams()
        val identifierId = normalized[UserApiPaths.USER_IDENTIFIER_ID]?.firstOrNull()?.takeIf { it.isNotBlank() }
        val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return IdentifierDetailParams(identifierId = identifierId, userId = userId)
    }

    /**
     * Serializes [IdentifierDetailParams] into a map of raw URL parameters.
     *
     * @param params Typed [IdentifierDetailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL query or path strings.
     */
    fun serialize(params: IdentifierDetailParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.identifierId != null) {
            result[UserApiPaths.USER_IDENTIFIER_ID] = listOf(params.identifierId)
        }
        if (params.userId != null) {
            result[UserApiPaths.USER_ID] = listOf(params.userId)
        }
        return result
    }
}
