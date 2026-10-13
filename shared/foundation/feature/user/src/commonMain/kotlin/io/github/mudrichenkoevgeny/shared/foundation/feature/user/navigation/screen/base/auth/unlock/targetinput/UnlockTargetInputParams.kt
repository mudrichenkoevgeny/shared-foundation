package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock target input screen (email or phone number entry).
 *
 * @property unlockMethod Selected unlock method ([UnlockMethod]); wire key is [UnlockMethod.PARAM_NAME].
 */
@Serializable
data class UnlockTargetInputParams(
    val unlockMethod: UnlockMethod? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        UnlockTargetInputParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): UnlockTargetInputParams =
            UnlockTargetInputParamsParser.parse(params)
    }
}
