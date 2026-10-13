package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams

/**
 * Bidirectional parser and serializer for [ManagementAuditEventDetailParams] URL query and path parameters.
 */
object ManagementAuditEventDetailParamsParser {

    /**
     * Parses raw URL query or path parameters into typed [ManagementAuditEventDetailParams].
     *
     * Accepts both canonical `Map<String, List<String>>` and flexible `Map<String, Any?>`
     * (e.g. single strings or arrays from JS/TS query objects).
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [ManagementAuditEventDetailParams] instance.
     */
    fun parse(params: Map<String, Any?>): ManagementAuditEventDetailParams {
        val normalized = params.normalizeQueryParams()
        val eventId = normalized[AuditApiPaths.EVENT_ID]?.firstOrNull()?.takeIf { it.isNotBlank() }
        return ManagementAuditEventDetailParams(eventId = eventId)
    }

    /**
     * Serializes [ManagementAuditEventDetailParams] into a map of raw URL parameter strings.
     *
     * @param params Typed [ManagementAuditEventDetailParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL query or path strings.
     */
    fun serialize(params: ManagementAuditEventDetailParams): Map<String, List<String>> {
        val eventId = params.eventId ?: return emptyMap()
        return mapOf(AuditApiPaths.EVENT_ID to listOf(eventId))
    }
}
