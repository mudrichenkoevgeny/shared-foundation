package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock method selection screen.
 *
 * @property unlockMethod Preferred unlock method ([UnlockMethod]); wire key is [UserNavigationPaths.UNLOCK_METHOD].
 * @property accountLockoutType Lockout cause ([AccountLockoutType]); wire key is [UserApiFields.ACCOUNT_LOCKOUT_TYPE].
 * @property temporaryLockoutUntil Epoch timestamp in milliseconds when the lockout expires; wire key is [UserApiFields.TEMPORARY_LOCKOUT_UNTIL].
 */
@Serializable
data class UnlockMethodSelectionParams(
    val unlockMethod: UnlockMethod? = null,
    val accountLockoutType: AccountLockoutType? = null,
    val temporaryLockoutUntil: Long? = null
) : NavigationScreenParams<UnlockMethodSelectionParams> {

    override val parser: NavigationScreenParamsParser<UnlockMethodSelectionParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<UnlockMethodSelectionParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [UnlockMethodSelectionParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [UnlockMethodSelectionParams] instance.
         */
        override fun parse(params: Map<String, Any?>): UnlockMethodSelectionParams {
            val normalized = params.normalizeQueryParams()

            val unlockMethod = normalized[UserNavigationPaths.UNLOCK_METHOD]
                ?.firstOrNull()
                ?.let { UnlockMethod.fromValueOrNull(it) }

            val lockoutType = normalized[UserApiFields.ACCOUNT_LOCKOUT_TYPE]
                ?.firstOrNull()
                ?.let { AccountLockoutType.fromValueOrNull(it) }

            val temporaryLockoutUntil = normalized[UserApiFields.TEMPORARY_LOCKOUT_UNTIL]
                ?.firstOrNull()
                ?.toLongOrNull()
                ?.takeIf { it > 0L }

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
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: UnlockMethodSelectionParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            if (params.unlockMethod != null) {
                result[UserNavigationPaths.UNLOCK_METHOD] = listOf(params.unlockMethod.value)
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
}