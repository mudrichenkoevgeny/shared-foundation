package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the password reset request screen.
 *
 * @property email Prefilled email address for password reset; wire key is [UserApiFields.EMAIL].
 * @property code Password reset confirmation code if received from deep link; wire key is [UserApiFields.CONFIRMATION_CODE].
 */
@Serializable
data class ResetPasswordParams(
    val email: String? = null,
    val code: String? = null
) : NavigationScreenParams<ResetPasswordParams> {

    override val parser: NavigationScreenParamsParser<ResetPasswordParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ResetPasswordParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ResetPasswordParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ResetPasswordParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ResetPasswordParams {
            val normalized = params.normalizeQueryParams()
            val email = normalized[UserApiFields.EMAIL]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            val code = normalized[UserApiFields.CONFIRMATION_CODE]?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            return ResetPasswordParams(email = email, code = code)
        }

        /**
         * Serializes [ResetPasswordParams] into a map of raw URL parameters.
         *
         * @param params Typed [ResetPasswordParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ResetPasswordParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            params.email?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserApiFields.EMAIL] = listOf(it)
            }
            params.code?.trim()?.takeIf { it.isNotBlank() }?.let {
                result[UserApiFields.CONFIRMATION_CODE] = listOf(it)
            }
            return result
        }
    }
}