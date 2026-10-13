package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [UnlockMethodSelectionParams] Deep Link and Web Router URL parameters.
 */
object UnlockMethodSelectionParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [UnlockMethodSelectionParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [UnlockMethodSelectionParams] instance.
     */
    fun parse(params: Map<String, Any?>): UnlockMethodSelectionParams {
        val normalized = params.normalizeQueryParams()

        val unlockMethod = normalized[UnlockMethod.PARAM_NAME]
            ?.firstOrNull()
            ?.let { UnlockMethod.fromValueOrNull(it) }

        val lockoutType = normalized[UserApiFields.ACCOUNT_LOCKOUT_TYPE]
            ?.firstOrNull()
            ?.let { AccountLockoutType.fromValueOrNull(it) }

        val temporaryLockoutUntil = normalized[UserApiFields.TEMPORARY_LOCKOUT_UNTIL]
            ?.firstOrNull()
            ?.toLongOrNull()

        return UnlockMethodSelectionParams(
            unlockMethod = unlockMethod,
            accountLockoutType = lockoutType,
            temporaryLockoutUntil = temporaryLockoutUntil
        )
    }

    /**
     * Serializes [UnlockMethodSelectionParams] into a map of raw URL parameters.
     *
     * @param params Typed [UnlockMethodSelectionParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: UnlockMethodSelectionParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        if (params.unlockMethod != null) {
            result[UnlockMethod.PARAM_NAME] = listOf(params.unlockMethod.value)
        }
        if (params.accountLockoutType != null) {
            result[UserApiFields.ACCOUNT_LOCKOUT_TYPE] = listOf(params.accountLockoutType.value)
        }
        if (params.temporaryLockoutUntil != null) {
            result[UserApiFields.TEMPORARY_LOCKOUT_UNTIL] = listOf(params.temporaryLockoutUntil.toString())
        }
        return result
    }
}
