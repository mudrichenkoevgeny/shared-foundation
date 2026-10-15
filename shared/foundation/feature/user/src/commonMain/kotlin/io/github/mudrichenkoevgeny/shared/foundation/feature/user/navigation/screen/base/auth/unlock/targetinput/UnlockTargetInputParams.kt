package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the account unlock target input screen (email or phone number entry).
 *
 * @property unlockMethod Selected unlock method ([UnlockMethod]); wire key is [UserNavigationPaths.UNLOCK_METHOD].
 */
@Serializable
data class UnlockTargetInputParams(
    val unlockMethod: UnlockMethod? = null
) : NavigationScreenParams<UnlockTargetInputParams> {

    override val parser: NavigationScreenParamsParser<UnlockTargetInputParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<UnlockTargetInputParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [UnlockTargetInputParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [UnlockTargetInputParams] instance.
         */
        override fun parse(params: Map<String, Any?>): UnlockTargetInputParams {
            val normalized = params.normalizeQueryParams()
            val unlockMethod = normalized[UserNavigationPaths.UNLOCK_METHOD]
                ?.firstOrNull()
                ?.let { UnlockMethod.fromValueOrNull(it) }

            return UnlockTargetInputParams(unlockMethod = unlockMethod)
        }

        /**
         * Serializes [UnlockTargetInputParams] into a map of raw URL parameters.
         *
         * @param params Typed [UnlockTargetInputParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: UnlockTargetInputParams): Map<String, List<String>> {
            val method = params.unlockMethod ?: return emptyMap()
            return mapOf(UserNavigationPaths.UNLOCK_METHOD to listOf(method.value))
        }
    }
}
