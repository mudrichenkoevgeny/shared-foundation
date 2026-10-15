package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the TOTP verification screen.
 *
 * @property mfaToken Temporary challenge token identifying the current MFA session; wire key is [SecurityApiFields.MFA_TOKEN].
 * @property loginByTotpMode Active verification mode ([LoginByTotpMode]); wire key is [UserNavigationPaths.LOGIN_BY_TOTP_MODE].
 * @property redirectUrl Target URL to navigate to upon successful verification; wire key is [UserNavigationPaths.REDIRECT_URL].
 */
@Serializable
data class LoginByTotpParams(
    val mfaToken: String? = null,
    val loginByTotpMode: LoginByTotpMode = LoginByTotpMode.DEFAULT,
    val redirectUrl: String? = null
) : NavigationScreenParams<LoginByTotpParams> {

    override val parser: NavigationScreenParamsParser<LoginByTotpParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<LoginByTotpParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [LoginByTotpParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [LoginByTotpParams] instance.
         */
        override fun parse(params: Map<String, Any?>): LoginByTotpParams {
            val normalized = params.normalizeQueryParams()
            val mfaToken = normalized[SecurityApiFields.MFA_TOKEN]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            val loginByTotpMode = normalized[UserNavigationPaths.LOGIN_BY_TOTP_MODE]
                ?.firstOrNull()
                ?.let { LoginByTotpMode.fromValueOrNull(it) }
                ?: LoginByTotpMode.DEFAULT
            val redirectUrl = normalized[UserNavigationPaths.REDIRECT_URL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }

            return LoginByTotpParams(
                mfaToken = mfaToken,
                loginByTotpMode = loginByTotpMode,
                redirectUrl = redirectUrl
            )
        }

        /**
         * Serializes [LoginByTotpParams] into a map of raw URL parameters.
         *
         * @param params Typed [LoginByTotpParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: LoginByTotpParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            params.mfaToken?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[SecurityApiFields.MFA_TOKEN] = listOf(it)
            }
            if (params.loginByTotpMode != LoginByTotpMode.DEFAULT) {
                result[UserNavigationPaths.LOGIN_BY_TOTP_MODE] = listOf(params.loginByTotpMode.value)
            }
            params.redirectUrl?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserNavigationPaths.REDIRECT_URL] = listOf(it)
            }
            return result
        }
    }
}