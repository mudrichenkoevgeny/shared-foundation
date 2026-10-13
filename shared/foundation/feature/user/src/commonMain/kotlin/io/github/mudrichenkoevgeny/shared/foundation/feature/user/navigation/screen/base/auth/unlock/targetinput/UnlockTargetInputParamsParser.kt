package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod

/**
 * Bidirectional parser and serializer for [UnlockTargetInputParams] Deep Link and Web Router URL parameters.
 */
object UnlockTargetInputParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [UnlockTargetInputParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [UnlockTargetInputParams] instance.
     */
    fun parse(params: Map<String, Any?>): UnlockTargetInputParams {
        val normalized = params.normalizeQueryParams()
        val unlockMethod = normalized[UnlockMethod.PARAM_NAME]
            ?.firstOrNull()
            ?.let { UnlockMethod.fromValueOrNull(it) }

        return UnlockTargetInputParams(unlockMethod = unlockMethod)
    }

    /**
     * Serializes [UnlockTargetInputParams] into a map of raw URL parameters.
     *
     * @param params Typed [UnlockTargetInputParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: UnlockTargetInputParams): Map<String, List<String>> {
        val method = params.unlockMethod ?: return emptyMap()
        return mapOf(UnlockMethod.PARAM_NAME to listOf(method.value))
    }
}
