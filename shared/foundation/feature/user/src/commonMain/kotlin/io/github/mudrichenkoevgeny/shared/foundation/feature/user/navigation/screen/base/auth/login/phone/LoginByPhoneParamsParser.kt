package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizePhoneQueryParam
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Bidirectional parser and serializer for [LoginByPhoneParams] Deep Link and Web Router URL parameters.
 */
object LoginByPhoneParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [LoginByPhoneParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [LoginByPhoneParams] instance.
     */
    fun parse(params: Map<String, Any?>): LoginByPhoneParams {
        val normalized = params.normalizeQueryParams()
        val phone = normalized[UserApiFields.PHONE_NUMBER]?.firstOrNull().normalizePhoneQueryParam()
        return LoginByPhoneParams(phone = phone)
    }

    /**
     * Serializes [LoginByPhoneParams] into a map of raw URL parameters.
     *
     * @param params Typed [LoginByPhoneParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: LoginByPhoneParams): Map<String, List<String>> {
        val phone = params.phone ?: return emptyMap()
        return mapOf(UserApiFields.PHONE_NUMBER to listOf(phone))
    }
}
