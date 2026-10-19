package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS

fun createTestAuditEventMetadata(
    key: AuditMetadataKey = CommonAuditMetadataKey.IP_ADDRESS,
    value: String = TEST_IP_ADDRESS
) = AuditEventMetadata(
    key = key,
    value = value
)
