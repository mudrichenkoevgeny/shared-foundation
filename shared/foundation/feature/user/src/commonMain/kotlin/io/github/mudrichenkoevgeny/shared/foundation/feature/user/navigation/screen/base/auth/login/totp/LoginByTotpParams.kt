package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.LoginByTotpMode
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the TOTP verification screen.
 *
 * @property mfaToken Temporary challenge token identifying the current MFA session; wire key is [SecurityApiFields.MFA_TOKEN].
 * @property loginByTotpMode Active verification mode ([LoginByTotpMode]); wire key is [LoginByTotpMode.PARAM_NAME].
 */
@Serializable
data class LoginByTotpParams(
    val mfaToken: String? = null,
    val loginByTotpMode: LoginByTotpMode? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        LoginByTotpParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): LoginByTotpParams =
            LoginByTotpParamsParser.parse(params)
    }
}
