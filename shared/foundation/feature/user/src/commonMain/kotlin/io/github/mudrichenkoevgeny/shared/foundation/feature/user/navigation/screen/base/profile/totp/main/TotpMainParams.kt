package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the TOTP 2FA main management screen.
 *
 * @property totpStep Active step/tab ([TotpStep]); wire key is [TotpStep.PARAM_NAME].
 */
@Serializable
data class TotpMainParams(
    val totpStep: TotpStep? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        TotpMainParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): TotpMainParams =
            TotpMainParamsParser.parse(params)
    }
}
