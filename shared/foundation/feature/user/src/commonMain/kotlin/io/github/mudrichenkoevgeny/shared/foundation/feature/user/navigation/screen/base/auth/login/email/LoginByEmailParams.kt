package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the login by email screen.
 *
 * @property email Prefilled email address for login; wire key is [UserApiFields.EMAIL].
 * @property redirectUrl Target URL to navigate to upon successful authentication; wire key is [UserNavigationPaths.REDIRECT_URL].
 */
@Serializable
data class LoginByEmailParams(
    val email: String? = null,
    val redirectUrl: String? = null
) : NavigationScreenParams<LoginByEmailParams> {

    override val parser: NavigationScreenParamsParser<LoginByEmailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<LoginByEmailParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [LoginByEmailParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [LoginByEmailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): LoginByEmailParams {
            val normalized = params.normalizeQueryParams()
            val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            val redirectUrl = normalized[UserNavigationPaths.REDIRECT_URL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return LoginByEmailParams(email = email, redirectUrl = redirectUrl)
        }

        /**
         * Serializes [LoginByEmailParams] into a map of raw URL parameters.
         *
         * @param params Typed [LoginByEmailParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: LoginByEmailParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            params.email?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserApiFields.EMAIL] = listOf(it)
            }
            params.redirectUrl?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserNavigationPaths.REDIRECT_URL] = listOf(it)
            }
            return result
        }
    }
}