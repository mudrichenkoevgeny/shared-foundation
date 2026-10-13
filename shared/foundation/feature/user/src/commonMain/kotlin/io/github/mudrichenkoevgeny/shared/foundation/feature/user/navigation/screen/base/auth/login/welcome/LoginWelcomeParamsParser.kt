package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.welcome

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.login.LoginStep

/**
 * Bidirectional parser and serializer for [LoginWelcomeParams] Deep Link and Web Router URL parameters.
 */
object LoginWelcomeParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [LoginWelcomeParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [LoginWelcomeParams] instance.
     */
    fun parse(params: Map<String, Any?>): LoginWelcomeParams {
        val normalized = params.normalizeQueryParams()
        val loginStep = normalized[LoginStep.PARAM_NAME]
            ?.firstOrNull()
            ?.let { LoginStep.fromValueOrNull(it) }

        return LoginWelcomeParams(loginStep = loginStep)
    }

    /**
     * Serializes [LoginWelcomeParams] into a map of raw URL parameters.
     *
     * @param params Typed [LoginWelcomeParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: LoginWelcomeParams): Map<String, List<String>> {
        val step = params.loginStep ?: return emptyMap()
        return mapOf(LoginStep.PARAM_NAME to listOf(step.value))
    }
}
