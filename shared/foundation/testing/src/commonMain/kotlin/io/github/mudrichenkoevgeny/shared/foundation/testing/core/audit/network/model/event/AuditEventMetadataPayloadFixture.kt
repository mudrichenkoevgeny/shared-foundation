package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS

fun createTestAuditEventMetadataPayload(
    key: String = CommonAuditMetadataKey.IP_ADDRESS.value,
    value: String = TEST_IP_ADDRESS,
    valueSensitivity: String = AuditValueSensitivity.NON_SENSITIVE.value
) = AuditEventMetadataPayload(
    key = key,
    value = value,
    valueSensitivity = valueSensitivity
)
