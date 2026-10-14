package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the management audit event detail screen.
 *
 * @property eventId Hex dash string representation of the target audit event ID; wire key is [AuditApiPaths.EVENT_ID].
 */
@Serializable
data class ManagementAuditEventDetailParams(
    val eventId: String
) : NavigationScreenParams {

    /**
     * Typed [AuditEventId] representation of [eventId], or `null` if invalid/absent.
     */
    val typedEventId: AuditEventId?
        get() = eventId.takeIf { it.isNotBlank() }?.toAuditEventIdOrNull()

    override fun toQueryMap(): Map<String, List<String>> =
        ManagementAuditEventDetailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ManagementAuditEventDetailParams =
            ManagementAuditEventDetailParamsParser.parse(params)
    }
}