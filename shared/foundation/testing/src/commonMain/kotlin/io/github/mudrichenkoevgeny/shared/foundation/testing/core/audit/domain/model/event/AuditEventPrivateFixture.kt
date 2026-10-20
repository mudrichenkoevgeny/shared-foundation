package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.metadata.createTestAuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID
import kotlin.time.Instant

/**
 * Creates a test [AuditEventPrivate] instance with default mock properties for unit testing.
 *
 * @param id Unique audit event ID ([AuditEventId]).
 * @param actorId ID of the actor initiating the action.
 * @param actorType Actor category ([AuditActorType]).
 * @param actorUserRole User role if applicable.
 * @param action Executed action ([AuditActionType]).
 * @param resource Target resource category ([AuditResourceType]).
 * @param resourceId Target resource ID.
 * @param status Outcome status ([AuditStatus]).
 * @param metadata Additional metadata entries.
 * @param message Human-readable log message.
 * @param createdAt Event timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestAuditEventPrivate(
    id: AuditEventId = createTestAuditEventId(),
    actorId: String? = TEST_USER_ID,
    actorType: AuditActorType = AuditActorType.USER,
    actorUserRole: String? = "USER",
    action: AuditActionType = UserAuditActionType.LOGIN_BY_EMAIL,
    resource: AuditResourceType = CommonAuditResourceType.AUDIT_EVENT,
    resourceId: String? = TEST_USER_ID,
    status: AuditStatus = AuditStatus.SUCCESS,
    metadata: Set<AuditEventMetadata> = setOf(createTestAuditEventMetadata()),
    message: String? = "User logged in successfully",
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = null
) = AuditEventPrivate(
    id = id,
    actorId = actorId,
    actorType = actorType,
    actorUserRole = actorUserRole,
    action = action,
    resource = resource,
    resourceId = resourceId,
    status = status,
    metadata = metadata,
    message = message,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Creates a list of test [AuditEventPrivate] instances with deterministic index-based IDs (`evt_1`, `evt_2`, ...),
 * alternating status, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestAuditEventPrivateList(
    count: Int = 2,
    generator: (index: Int) -> AuditEventPrivate = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestAuditEventPrivate(
            id = createTestAuditEventId("evt_${index + 1}"),
            status = if (index % 2 == 0) AuditStatus.SUCCESS else AuditStatus.FAILED,
            createdAt = Instant.fromEpochMilliseconds(createdAtMs)
        )
    }
): List<AuditEventPrivate> = createTestList(count, generator)
