package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo

fun ClientInfo.toAuditMetadata(): Set<AuditEventMetadata> {
    val clientInfo = this
    val deviceInfo = clientInfo.deviceInfo

    return buildSet {
        deviceInfo.deviceId?.asHexDashString()?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.DEVICE_ID, value))
        }
        deviceInfo.deviceName?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.DEVICE_NAME, value))
        }
        deviceInfo.clientType?.serialName?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.CLIENT_TYPE, value))
        }
        deviceInfo.language?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.LANGUAGE, value))
        }
        deviceInfo.appVersion?.takeIf { it.isNotBlank() }?.let { value ->
            add(AuditEventMetadata(CommonAuditMetadataKey.APP_VERSION, value))
        }
        deviceInfo.operationSystemVersion?.takeIf { it.isNotBlank() }?.let { value ->
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