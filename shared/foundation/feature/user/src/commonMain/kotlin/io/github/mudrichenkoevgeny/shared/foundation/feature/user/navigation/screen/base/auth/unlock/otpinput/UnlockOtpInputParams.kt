package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizePhoneQueryParam
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the unified account unlock OTP verification screen.
 *
 * @property unlockMethod Selected unlock method ([UnlockMethod]); wire key is [UserNavigationPaths.UNLOCK_METHOD].
 * @property identifier Target email address or phone number; wire key is [UserApiFields.IDENTIFIER].
 * @property retryAfterSeconds Cooldown delay in seconds before a new OTP can be requested; wire key is [SecurityApiFields.RETRY_AFTER_SECONDS].
 * @property code Pre-filled verification code from deep link; wire key is [UserApiFields.CONFIRMATION_CODE].
 */
@Serializable
data class UnlockOtpInputParams(
    val unlockMethod: UnlockMethod? = null,
    val identifier: String? = null,
    val retryAfterSeconds: Int? = null,
    val code: String? = null
) : NavigationScreenParams<UnlockOtpInputParams> {

    override val parser: NavigationScreenParamsParser<UnlockOtpInputParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<UnlockOtpInputParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [UnlockOtpInputParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [UnlockOtpInputParams] instance.
         */
        override fun parse(params: Map<String, Any?>): UnlockOtpInputParams {
            val normalized = params.normalizeQueryParams()

            val unlockMethod = normalized[UserNavigationPaths.UNLOCK_METHOD]
                ?.firstOrNull()
                ?.let { UnlockMethod.fromValueOrNull(it) }

            val rawIdentifier = normalized[UserApiFields.IDENTIFIER]?.firstOrNull()?.trim()
            val identifier = if (unlockMethod == UnlockMethod.PHONE) {
                rawIdentifier.normalizePhoneQueryParam()
            } else {
                rawIdentifier?.takeIf { it.isNotBlank() }
            }

            val retryAfterSeconds = normalized[SecurityApiFields.RETRY_AFTER_SECONDS]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it >= 0 }

            val code = normalized[UserApiFields.CONFIRMATION_CODE]
                ?.firstOrNull()
                ?.trim()
                ?.takeIf { it.isNotBlank() }

            return UnlockOtpInputParams(
                unlockMethod = unlockMethod,
                identifier = identifier,
                retryAfterSeconds = retryAfterSeconds,
                code = code
            )
        }

        /**
         * Serializes [UnlockOtpInputParams] into a map of raw URL parameters.
         *
         * @param params Typed [UnlockOtpInputParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: UnlockOtpInputParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            if (params.unlockMethod != null) {
                result[UserNavigationPaths.UNLOCK_METHOD] = listOf(params.unlockMethod.value)
            }
            val identifier = params.identifier?.trim()?.takeIf { it.isNotBlank() }
            if (identifier != null) {
                result[UserApiFields.IDENTIFIER] = listOf(identifier)
            }
            if (params.retryAfterSeconds != null) {
                result[SecurityApiFields.RETRY_AFTER_SECONDS] = listOf(params.retryAfterSeconds.toString())
            }
            val code = params.code?.trim()?.takeIf { it.isNotBlank() }
            if (code != null) {
                result[UserApiFields.CONFIRMATION_CODE] = listOf(code)
            }
            return result
        }
    }
}