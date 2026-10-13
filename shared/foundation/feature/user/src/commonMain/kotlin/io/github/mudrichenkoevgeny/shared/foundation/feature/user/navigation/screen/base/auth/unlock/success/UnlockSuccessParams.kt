package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock success screen.
 *
 * @property unlockMethod Method used to complete unlock ([UnlockMethod]); wire key is [UnlockMethod.PARAM_NAME].
 */
@Serializable
data class UnlockSuccessParams(
    val unlockMethod: UnlockMethod? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        UnlockSuccessParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): UnlockSuccessParams =
            UnlockSuccessParamsParser.parse(params)
    }
}
