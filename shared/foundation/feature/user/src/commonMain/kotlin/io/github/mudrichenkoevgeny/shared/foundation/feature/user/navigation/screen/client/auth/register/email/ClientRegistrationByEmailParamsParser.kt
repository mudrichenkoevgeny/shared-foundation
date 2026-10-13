package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.register.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [ClientRegistrationByEmailParams] Deep Link and Web Router URL parameters.
 */
object ClientRegistrationByEmailParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [ClientRegistrationByEmailParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [ClientRegistrationByEmailParams] instance.
     */
    fun parse(params: Map<String, Any?>): ClientRegistrationByEmailParams {
        val normalized = params.normalizeQueryParams()
        val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return ClientRegistrationByEmailParams(email = email)
    }

    /**
     * Serializes [ClientRegistrationByEmailParams] into a map of raw URL parameters.
     *
     * @param params Typed [ClientRegistrationByEmailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: ClientRegistrationByEmailParams): Map<String, List<String>> {
        val email = params.email ?: return emptyMap()
        return mapOf(UserApiFields.EMAIL to listOf(email))
    }
}
