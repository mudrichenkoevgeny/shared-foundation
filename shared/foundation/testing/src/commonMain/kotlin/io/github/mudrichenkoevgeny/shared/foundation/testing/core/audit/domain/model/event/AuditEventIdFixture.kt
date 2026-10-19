package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow

const val TEST_AUDIT_EVENT_ID = "00000000-0000-0000-0000-000000000099"

fun createTestAuditEventId(
    value: String = TEST_AUDIT_EVENT_ID
): AuditEventId = value.toAuditEventIdOrThrow()
