package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow

/** Default deterministic audit event ID string for unit tests. */
const val TEST_AUDIT_EVENT_ID = "00000000-0000-4000-8000-000000000099"

/**
 * Creates a test [AuditEventId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_AUDIT_EVENT_ID]).
 */
fun createTestAuditEventId(
    value: String = TEST_AUDIT_EVENT_ID
): AuditEventId = value.toAuditEventIdOrThrow()
