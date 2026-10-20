package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.TEST_AUDIT_EVENT_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [AuditEventPrivatePayload] wire object for unit testing mapper logic.
 *
 * @param id Raw audit event ID string.
 * @param actorId Raw actor ID string.
 * @param actorType Wire string representation of [AuditActorType].
 * @param actorUserRole User role string.
 * @param action Executed action wire string.
 * @param resource Resource category wire string.
 * @param resourceId Target resource ID string.
 * @param status Wire string representation of [AuditStatus].
 * @param metadata Additional metadata payloads.
 * @param message Human-readable message string.
 * @param createdAt Event epoch-millisecond timestamp.
 * @param updatedAt Modification epoch-millisecond timestamp.
 */
fun createTestAuditEventPrivatePayload(
    id: String = TEST_AUDIT_EVENT_ID,
    actorId: String? = TEST_USER_ID,
    actorType: String = AuditActorType.USER.value,
    actorUserRole: String? = "USER",
    action: String = UserAuditActionType.LOGIN_BY_EMAIL.value,
    resource: String = CommonAuditResourceType.AUDIT_EVENT.value,
    resourceId: String? = TEST_USER_ID,
    status: String = AuditStatus.SUCCESS.value,
    metadata: List<AuditEventMetadataPayload> = listOf(createTestAuditEventMetadataPayload()),
    message: String? = "User logged in successfully",
    createdAt: Long = TEST_TIME_MILLIS,
    updatedAt: Long? = null
) = AuditEventPrivatePayload(
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
 * Creates a list of test [AuditEventPrivatePayload] wire objects with deterministic raw IDs (`evt_1`, `evt_2`, ...),
 * alternating status, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestAuditEventPrivatePayloadList(
    count: Int = 2,
    generator: (index: Int) -> AuditEventPrivatePayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestAuditEventPrivatePayload(
            id = "evt_${index + 1}",
            status = if (index % 2 == 0) AuditStatus.SUCCESS.value else AuditStatus.FAILED.value,
            createdAt = createdAtMs
        )
    }
): List<AuditEventPrivatePayload> = createTestList(count, generator)
