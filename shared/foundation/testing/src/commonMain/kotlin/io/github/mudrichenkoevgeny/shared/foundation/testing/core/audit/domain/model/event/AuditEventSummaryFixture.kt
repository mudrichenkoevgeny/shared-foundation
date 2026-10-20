package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import kotlin.time.Instant

/**
 * Creates a test [AuditEventSummary] instance with default mock properties for unit testing.
 *
 * @param id Unique audit event ID ([AuditEventId]).
 * @param actorType Actor category ([AuditActorType]).
 * @param action Executed action ([AuditActionType]).
 * @param resource Target resource category ([AuditResourceType]).
 * @param status Outcome status ([AuditStatus]).
 * @param createdAt Event timestamp.
 */
fun createTestAuditEventSummary(
    id: AuditEventId = createTestAuditEventId(),
    actorType: AuditActorType = AuditActorType.USER,
    action: AuditActionType = UserAuditActionType.LOGIN_BY_EMAIL,
    resource: AuditResourceType = CommonAuditResourceType.AUDIT_EVENT,
    status: AuditStatus = AuditStatus.SUCCESS,
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS)
) = AuditEventSummary(
    id = id,
    actorType = actorType,
    action = action,
    resource = resource,
    status = status,
    createdAt = createdAt
)

/**
 * Creates a list of test [AuditEventSummary] instances with deterministic index-based IDs (`evt_1`, `evt_2`, ...),
 * alternating status, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestAuditEventSummaryList(
    count: Int = 2,
    generator: (index: Int) -> AuditEventSummary = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestAuditEventSummary(
            id = createTestAuditEventId("evt_${index + 1}"),
            status = if (index % 2 == 0) AuditStatus.SUCCESS else AuditStatus.FAILED,
            createdAt = Instant.fromEpochMilliseconds(createdAtMs)
        )
    }
): List<AuditEventSummary> = createTestList(count, generator)
