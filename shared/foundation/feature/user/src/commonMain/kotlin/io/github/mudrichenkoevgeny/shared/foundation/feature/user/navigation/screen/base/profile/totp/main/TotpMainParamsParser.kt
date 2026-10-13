package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep

/**
 * Bidirectional parser and serializer for [TotpMainParams] Deep Link and Web Router URL parameters.
 */
object TotpMainParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [TotpMainParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [TotpMainParams] instance.
     */
    fun parse(params: Map<String, Any?>): TotpMainParams {
        val normalized = params.normalizeQueryParams()
        val step = normalized[TotpStep.PARAM_NAME]
            ?.firstOrNull()
            ?.let { TotpStep.fromValueOrNull(it) }

        return TotpMainParams(totpStep = step)
    }

    /**
     * Serializes [TotpMainParams] into a map of raw URL parameters.
     *
     * @param params Typed [TotpMainParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: TotpMainParams): Map<String, List<String>> {
        val step = params.totpStep ?: return emptyMap()
        return mapOf(TotpStep.PARAM_NAME to listOf(step.value))
    }
}
