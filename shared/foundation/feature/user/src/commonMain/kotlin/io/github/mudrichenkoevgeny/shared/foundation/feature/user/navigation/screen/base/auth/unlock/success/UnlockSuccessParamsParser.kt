package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod

/**
 * Bidirectional parser and serializer for [UnlockSuccessParams] Deep Link and Web Router URL parameters.
 */
object UnlockSuccessParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [UnlockSuccessParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [UnlockSuccessParams] instance.
     */
    fun parse(params: Map<String, Any?>): UnlockSuccessParams {
        val normalized = params.normalizeQueryParams()
        val method = normalized[UnlockMethod.PARAM_NAME]
            ?.firstOrNull()
            ?.let { UnlockMethod.fromValueOrNull(it) }

        return UnlockSuccessParams(unlockMethod = method)
    }

    /**
     * Serializes [UnlockSuccessParams] into a map of raw URL parameters.
     *
     * @param params Typed [UnlockSuccessParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: UnlockSuccessParams): Map<String, List<String>> {
        val method = params.unlockMethod ?: return emptyMap()
        return mapOf(UnlockMethod.PARAM_NAME to listOf(method.value))
    }
}
