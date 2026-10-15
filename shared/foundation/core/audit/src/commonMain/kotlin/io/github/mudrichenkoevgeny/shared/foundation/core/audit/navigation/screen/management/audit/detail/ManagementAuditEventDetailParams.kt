package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the management audit event detail screen.
 *
 * @property eventId Hex dash string representation of the target audit event ID; wire key is [AuditApiPaths.EVENT_ID].
 */
@Serializable
data class ManagementAuditEventDetailParams(
    val eventId: String
) : NavigationScreenParams<ManagementAuditEventDetailParams> {

    override val parser: NavigationScreenParamsParser<ManagementAuditEventDetailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementAuditEventDetailParams> {

        /**
         * Parses raw URL query or path parameters into typed [ManagementAuditEventDetailParams].
         *
         * Accepts both canonical `Map<String, List<String>>` and flexible `Map<String, Any?>`
         * (e.g. single strings or arrays from JS/TS query objects).
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementAuditEventDetailParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementAuditEventDetailParams {
            val normalized = params.normalizeQueryParams()
            val eventId = normalized[AuditApiPaths.EVENT_ID]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }
                ?: ""
            return ManagementAuditEventDetailParams(eventId = eventId)
        }

        /**
         * Serializes [ManagementAuditEventDetailParams] into a map of URL query parameter strings.
         *
         * Returns an empty map because the event ID is represented as a path parameter in the route URL
         * rather than a query parameter.
         *
         * @param params Typed [ManagementAuditEventDetailParams] to serialize.
         * @return Empty map as no query parameters are associated with this screen.
         */
        override fun serialize(
            params: ManagementAuditEventDetailParams
        ): Map<String, List<String>> = emptyMap()
    }
}