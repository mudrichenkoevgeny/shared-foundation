package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Bidirectional parser and serializer for [UserDetailParams] Deep Link and Web Router URL parameters.
 */
object UserDetailParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [UserDetailParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [UserDetailParams] instance.
     */
    fun parse(params: Map<String, Any?>): UserDetailParams {
        val normalized = params.normalizeQueryParams()
        val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return UserDetailParams(userId = userId)
    }

    /**
     * Serializes [UserDetailParams] into a map of raw URL parameters.
     *
     * @param params Typed [UserDetailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: UserDetailParams): Map<String, List<String>> {
        val userId = params.userId ?: return emptyMap()
        return mapOf(UserApiPaths.USER_ID to listOf(userId))
    }
}
