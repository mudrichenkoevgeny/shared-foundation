package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the TOTP 2FA main management screen.
 *
 * @property totpStep Active step/tab ([TotpStep]); wire key is [UserNavigationPaths.TOTP_STEP].
 */
@Serializable
data class TotpMainParams(
    val totpStep: TotpStep = TotpStep.DEFAULT
) : NavigationScreenParams<TotpMainParams> {

    override val parser: NavigationScreenParamsParser<TotpMainParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<TotpMainParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [TotpMainParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [TotpMainParams] instance with defaults applied.
         */
        override fun parse(params: Map<String, Any?>): TotpMainParams {
            val normalized = params.normalizeQueryParams()
            val step = normalized[UserNavigationPaths.TOTP_STEP]
                ?.firstOrNull()
                ?.let { TotpStep.fromValueOrNull(it) }
                ?: TotpStep.DEFAULT

            return TotpMainParams(totpStep = step)
        }

        /**
         * Serializes [TotpMainParams] into a map of raw URL parameters.
         *
         * Omits default step value to keep URLs clean.
         *
         * @param params Typed [TotpMainParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: TotpMainParams): Map<String, List<String>> {
            if (params.totpStep == TotpStep.DEFAULT) return emptyMap()
            return mapOf(UserNavigationPaths.TOTP_STEP to listOf(params.totpStep.value))
        }
    }
}