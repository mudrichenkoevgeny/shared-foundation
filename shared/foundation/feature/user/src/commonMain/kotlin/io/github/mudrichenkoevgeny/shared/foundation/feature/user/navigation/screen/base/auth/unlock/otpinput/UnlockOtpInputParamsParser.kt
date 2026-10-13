package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizePhoneQueryParam
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [UnlockOtpInputParams] Deep Link and Web Router URL parameters.
 */
object UnlockOtpInputParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [UnlockOtpInputParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [UnlockOtpInputParams] instance.
     */
    fun parse(params: Map<String, Any?>): UnlockOtpInputParams {
        val normalized = params.normalizeQueryParams()

        val unlockMethod = normalized[UnlockMethod.PARAM_NAME]
            ?.firstOrNull()
            ?.let { UnlockMethod.fromValueOrNull(it) }

        val rawIdentifier = normalized[UserApiFields.IDENTIFIER]?.firstOrNull()
        val identifier = if (unlockMethod == UnlockMethod.PHONE) {
            rawIdentifier.normalizePhoneQueryParam()
        } else {
            rawIdentifier?.takeIf { it.isNotBlank() }
        }

        val retryAfterSeconds = normalized[SecurityApiFields.RETRY_AFTER_SECONDS]
            ?.firstOrNull()
            ?.toIntOrNull()

        val code = normalized[UserApiFields.CONFIRMATION_CODE]
            ?.firstOrNull()
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
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: UnlockOtpInputParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.unlockMethod != null) {
            result[UnlockMethod.PARAM_NAME] = listOf(params.unlockMethod.value)
        }
        if (params.identifier != null) {
            result[UserApiFields.IDENTIFIER] = listOf(params.identifier)
        }
        if (params.retryAfterSeconds != null) {
            result[SecurityApiFields.RETRY_AFTER_SECONDS] = listOf(params.retryAfterSeconds.toString())
        }
        if (params.code != null) {
            result[UserApiFields.CONFIRMATION_CODE] = listOf(params.code)
        }
        return result
    }
}
