package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the login by phone screen.
 *
 * @property phone Prefilled phone number for login; wire key is [UserApiFields.PHONE_NUMBER].
 */
@Serializable
data class LoginByPhoneParams(
    val phone: String? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        LoginByPhoneParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): LoginByPhoneParams =
            LoginByPhoneParamsParser.parse(params)
    }
}
