package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.LoginByTotpMode

/**
 * Bidirectional parser and serializer for [LoginByTotpParams] Deep Link and Web Router URL parameters.
 */
object LoginByTotpParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [LoginByTotpParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [LoginByTotpParams] instance.
     */
    fun parse(params: Map<String, Any?>): LoginByTotpParams {
        val normalized = params.normalizeQueryParams()
        val mfaToken = normalized[SecurityApiFields.MFA_TOKEN]?.firstOrNull()?.takeIf { it.isNotBlank() }
        val loginByTotpMode = normalized[LoginByTotpMode.PARAM_NAME]
            ?.firstOrNull()
            ?.let { LoginByTotpMode.fromValueOrNull(it) }

        return LoginByTotpParams(
            mfaToken = mfaToken,
            loginByTotpMode = loginByTotpMode
        )
    }

    /**
     * Serializes [LoginByTotpParams] into a map of raw URL parameters.
     *
     * @param params Typed [LoginByTotpParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: LoginByTotpParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.mfaToken != null) {
            result[SecurityApiFields.MFA_TOKEN] = listOf(params.mfaToken)
        }
        if (params.loginByTotpMode != null) {
            result[LoginByTotpMode.PARAM_NAME] = listOf(params.loginByTotpMode.value)
        }
        return result
    }
}
