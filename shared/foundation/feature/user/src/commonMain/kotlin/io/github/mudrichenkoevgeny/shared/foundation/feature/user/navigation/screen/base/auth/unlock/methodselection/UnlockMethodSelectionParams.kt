package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock method selection screen.
 *
 * @property unlockMethod Preferred unlock method ([UnlockMethod]); wire key is [UnlockMethod.PARAM_NAME].
 * @property accountLockoutType Lockout cause ([AccountLockoutType]); wire key is [UserApiFields.ACCOUNT_LOCKOUT_TYPE].
 * @property temporaryLockoutUntil Epoch timestamp in milliseconds when the lockout expires; wire key is [UserApiFields.TEMPORARY_LOCKOUT_UNTIL].
 */
@Serializable
data class UnlockMethodSelectionParams(
    val unlockMethod: UnlockMethod? = null,
    val accountLockoutType: AccountLockoutType? = null,
    val temporaryLockoutUntil: Long? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        UnlockMethodSelectionParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): UnlockMethodSelectionParams =
            UnlockMethodSelectionParamsParser.parse(params)
    }
}
