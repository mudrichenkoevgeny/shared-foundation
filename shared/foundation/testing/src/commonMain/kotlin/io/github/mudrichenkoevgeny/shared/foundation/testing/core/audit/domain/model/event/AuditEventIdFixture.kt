package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow

/**
 * Default audit event ID string for unit testing.
 */
const val TEST_AUDIT_EVENT_ID = "00000000-0000-0000-0000-000000000099"

/**
 * Creates a test [AuditEventId] domain instance.
 *
 * @param value Raw audit event ID string.
 */
fun createTestAuditEventId(
    value: String = TEST_AUDIT_EVENT_ID
): AuditEventId = value.toAuditEventIdOrThrow()
