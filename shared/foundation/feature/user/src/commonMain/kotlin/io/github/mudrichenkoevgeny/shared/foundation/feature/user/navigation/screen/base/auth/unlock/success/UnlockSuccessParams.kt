package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock success screen.
 *
 * @property unlockMethod Method used to complete unlock ([UnlockMethod]); wire key is [UserNavigationPaths.UNLOCK_METHOD].
 */
@Serializable
data class UnlockSuccessParams(
    val unlockMethod: UnlockMethod? = null
) : NavigationScreenParams<UnlockSuccessParams> {

    override val parser: NavigationScreenParamsParser<UnlockSuccessParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<UnlockSuccessParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [UnlockSuccessParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [UnlockSuccessParams] instance.
         */
        override fun parse(params: Map<String, Any?>): UnlockSuccessParams {
            val normalized = params.normalizeQueryParams()
            val method = normalized[UserNavigationPaths.UNLOCK_METHOD]
                ?.firstOrNull()
                ?.let { UnlockMethod.fromValueOrNull(it) }

            return UnlockSuccessParams(unlockMethod = method)
        }

        /**
         * Serializes [UnlockSuccessParams] into a map of raw URL parameters.
         *
         * @param params Typed [UnlockSuccessParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: UnlockSuccessParams): Map<String, List<String>> {
            val method = params.unlockMethod ?: return emptyMap()
            return mapOf(UserNavigationPaths.UNLOCK_METHOD to listOf(method.value))
        }
    }
}
