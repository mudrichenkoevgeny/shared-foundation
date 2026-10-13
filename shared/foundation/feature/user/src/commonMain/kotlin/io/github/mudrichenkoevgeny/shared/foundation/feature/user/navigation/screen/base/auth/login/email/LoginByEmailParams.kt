package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the login by email screen.
 *
 * @property email Prefilled email address for login; wire key is [UserApiFields.EMAIL].
 */
@Serializable
data class LoginByEmailParams(
    val email: String? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        LoginByEmailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): LoginByEmailParams =
            LoginByEmailParamsParser.parse(params)
    }
}
