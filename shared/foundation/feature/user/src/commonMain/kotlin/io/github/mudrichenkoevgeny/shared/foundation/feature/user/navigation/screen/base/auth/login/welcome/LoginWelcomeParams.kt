package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.welcome

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.login.LoginStep
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the entry login welcome screen.
 *
 * @property loginStep Initial active login step ([LoginStep]); wire key is [LoginStep.PARAM_NAME].
 */
@Serializable
data class LoginWelcomeParams(
    val loginStep: LoginStep? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        LoginWelcomeParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): LoginWelcomeParams =
            LoginWelcomeParamsParser.parse(params)
    }
}
