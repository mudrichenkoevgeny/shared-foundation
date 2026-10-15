package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the client registration screen.
 *
 * @property email Prefilled email address for registration; wire key is [UserApiFields.EMAIL].
 * @property redirectUrl Target URL to navigate to upon successful registration; wire key is [UserNavigationPaths.REDIRECT_URL].
 */
@Serializable
data class ClientRegistrationByEmailParams(
    val email: String? = null,
    val redirectUrl: String? = null
) : NavigationScreenParams<ClientRegistrationByEmailParams> {

    override val parser: NavigationScreenParamsParser<ClientRegistrationByEmailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ClientRegistrationByEmailParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ClientRegistrationByEmailParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ClientRegistrationByEmailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ClientRegistrationByEmailParams {
            val normalized = params.normalizeQueryParams()
            val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            val redirectUrl = normalized[UserNavigationPaths.REDIRECT_URL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return ClientRegistrationByEmailParams(email = email, redirectUrl = redirectUrl)
        }

        /**
         * Serializes [ClientRegistrationByEmailParams] into a map of raw URL query parameters.
         *
         * @param params Typed [ClientRegistrationByEmailParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ClientRegistrationByEmailParams): Map<String, List<String>> {
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