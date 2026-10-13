package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the unified account unlock OTP verification screen.
 *
 * @property unlockMethod Selected unlock method ([UnlockMethod]); wire key is [UnlockMethod.PARAM_NAME].
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
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        UnlockOtpInputParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): UnlockOtpInputParams =
            UnlockOtpInputParamsParser.parse(params)
    }
}
