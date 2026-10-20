package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.TEST_AUDIT_EVENT_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList

/**
 * Creates a test [AuditEventSummaryPayload] wire object for unit testing mapper logic.
 *
 * @param id Raw audit event ID string.
 * @param actorType Wire string representation of [AuditActorType].
 * @param action Executed action wire string.
 * @param resource Resource category wire string.
 * @param status Wire string representation of [AuditStatus].
 * @param createdAt Event epoch-millisecond timestamp.
 */
fun createTestAuditEventSummaryPayload(
    id: String = TEST_AUDIT_EVENT_ID,
    actorType: String = AuditActorType.USER.value,
    action: String = UserAuditActionType.LOGIN_BY_EMAIL.value,
    resource: String = CommonAuditResourceType.AUDIT_EVENT.value,
    status: String = AuditStatus.SUCCESS.value,
    createdAt: Long = TEST_TIME_MILLIS
) = AuditEventSummaryPayload(
    id = id,
    actorType = actorType,
    action = action,
    resource = resource,
    status = status,
    createdAt = createdAt
)

/**
 * Creates a list of test [AuditEventSummaryPayload] wire objects with deterministic raw IDs (`evt_1`, `evt_2`, ...),
 * alternating status, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestAuditEventSummaryPayloadList(
    count: Int = 2,
    generator: (index: Int) -> AuditEventSummaryPayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestAuditEventSummaryPayload(
            id = "evt_${index + 1}",
            status = if (index % 2 == 0) AuditStatus.SUCCESS.value else AuditStatus.FAILED.value,
            createdAt = createdAtMs
        )
    }
): List<AuditEventSummaryPayload> = createTestList(count, generator)
