package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.navigation.screen.management.audit.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail.ManagementAuditEventDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.TEST_AUDIT_EVENT_ID

fun createTestManagementAuditEventDetailParams(
    eventId: String = TEST_AUDIT_EVENT_ID
): ManagementAuditEventDetailParams = ManagementAuditEventDetailParams(
    eventId = eventId
)