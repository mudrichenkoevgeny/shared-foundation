package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo

/**
 * Converts [ClientInfo] into a set of [AuditEventMetadata] entries for audit logging.
 *
 * @return set of populated audit metadata key-value pairs derived from client and device info.
 */
fun ClientInfo.toAuditMetadata(): Set<AuditEventMetadata> {
    val clientInfo = this
    val clientDeviceInfo = clientInfo.clientDeviceInfo

    return buildSet {
        clientDeviceInfo.deviceId?.asHexDashString()?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.DEVICE_ID, value))
        }
        clientDeviceInfo.deviceName?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.DEVICE_NAME, value))
        }
        clientDeviceInfo.clientType?.serialName?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.CLIENT_TYPE, value))
        }
        clientDeviceInfo.language?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.LANGUAGE, value))
        }
        clientDeviceInfo.appVersion?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.APP_VERSION, value))
        }
        clientDeviceInfo.operationSystemVersion?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.OS_VERSION, value))
        }
        clientInfo.userAgent?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.USER_AGENT, value))
        }
        clientInfo.ipAddress?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.IP_ADDRESS, value))
        }
        clientInfo.host?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.HOST, value))
        }
        clientInfo.origin?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.ORIGIN, value))
        }
        clientInfo.apiVersion?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.API_VERSION, value))
        }
    }
}
