package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizePhoneQueryParam
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the login by phone screen.
 *
 * @property phone Prefilled phone number for login; wire key is [UserApiFields.PHONE_NUMBER].
 * @property redirectUrl Target URL to navigate to upon successful authentication; wire key is [UserNavigationPaths.REDIRECT_URL].
 */
@Serializable
data class LoginByPhoneParams(
    val phone: String? = null,
    val redirectUrl: String? = null
) : NavigationScreenParams<LoginByPhoneParams> {

    override val parser: NavigationScreenParamsParser<LoginByPhoneParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<LoginByPhoneParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [LoginByPhoneParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [LoginByPhoneParams] instance.
         */
        override fun parse(params: Map<String, Any?>): LoginByPhoneParams {
            val normalized = params.normalizeQueryParams()
            val phone = normalized[UserApiFields.PHONE_NUMBER]?.firstOrNull().normalizePhoneQueryParam()
            val redirectUrl = normalized[UserNavigationPaths.REDIRECT_URL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return LoginByPhoneParams(phone = phone, redirectUrl = redirectUrl)
        }

        /**
         * Serializes [LoginByPhoneParams] into a map of raw URL parameters.
         *
         * @param params Typed [LoginByPhoneParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: LoginByPhoneParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            params.phone?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserApiFields.PHONE_NUMBER] = listOf(it)
            }
            params.redirectUrl?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserNavigationPaths.REDIRECT_URL] = listOf(it)
            }
            return result
        }
    }
}