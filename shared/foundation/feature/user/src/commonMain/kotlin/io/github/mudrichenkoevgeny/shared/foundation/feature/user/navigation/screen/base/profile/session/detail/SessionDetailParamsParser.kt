package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Bidirectional parser and serializer for [SessionDetailParams] Deep Link and Web Router URL query and path parameters.
 */
object SessionDetailParamsParser {

    /**
     * Parses raw Deep Link or Web Router query/path parameters into typed [SessionDetailParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [SessionDetailParams] instance.
     */
    fun parse(params: Map<String, Any?>): SessionDetailParams {
        val normalized = params.normalizeQueryParams()
        val sessionId = normalized[UserApiPaths.SESSION_ID]?.firstOrNull()?.takeIf { it.isNotBlank() } ?: ""
        val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return SessionDetailParams(sessionId = sessionId, userId = userId)
    }

    /**
     * Serializes [SessionDetailParams] into a map of raw URL parameters.
     *
     * @param params Typed [SessionDetailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL query or path strings.
     */
    fun serialize(params: SessionDetailParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.sessionId.isNotBlank()) {
            result[UserApiPaths.SESSION_ID] = listOf(params.sessionId)
        }
        if (params.userId != null) {
            result[UserApiPaths.USER_ID] = listOf(params.userId)
        }
        return result
    }
}
