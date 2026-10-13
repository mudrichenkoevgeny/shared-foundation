package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [ResetPasswordParams] Deep Link and Web Router URL parameters.
 */
object ResetPasswordParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [ResetPasswordParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [ResetPasswordParams] instance.
     */
    fun parse(params: Map<String, Any?>): ResetPasswordParams {
        val normalized = params.normalizeQueryParams()
        val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.takeIf { it.isNotBlank() }
        val code = normalized[UserApiFields.CONFIRMATION_CODE]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return ResetPasswordParams(email = email, code = code)
    }

    /**
     * Serializes [ResetPasswordParams] into a map of raw URL parameters.
     *
     * @param params Typed [ResetPasswordParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: ResetPasswordParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.email != null) {
            result[UserApiFields.EMAIL] = listOf(params.email)
        }
        if (params.code != null) {
            result[UserApiFields.CONFIRMATION_CODE] = listOf(params.code)
        }
        return result
    }
}
