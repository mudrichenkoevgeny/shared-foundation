package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
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
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ResetPasswordParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ResetPasswordParams =
            ResetPasswordParamsParser.parse(params)
    }
}
