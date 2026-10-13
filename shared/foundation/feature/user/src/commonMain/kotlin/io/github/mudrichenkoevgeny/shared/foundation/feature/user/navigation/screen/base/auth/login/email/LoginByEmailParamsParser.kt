package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [LoginByEmailParams] Deep Link and Web Router URL parameters.
 */
object LoginByEmailParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [LoginByEmailParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [LoginByEmailParams] instance.
     */
    fun parse(params: Map<String, Any?>): LoginByEmailParams {
        val normalized = params.normalizeQueryParams()
        val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return LoginByEmailParams(email = email)
    }

    /**
     * Serializes [LoginByEmailParams] into a map of raw URL parameters.
     *
     * @param params Typed [LoginByEmailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: LoginByEmailParams): Map<String, List<String>> {
        val email = params.email ?: return emptyMap()
        return mapOf(UserApiFields.EMAIL to listOf(email))
    }
}
