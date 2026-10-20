package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.metadata.createTestAuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID
import kotlin.time.Instant

/**
 * Creates a test [AuditEventInternal] instance with default mock properties for unit testing.
 *
 * @param id Unique audit event ID ([AuditEventId]).
 * @param actorId ID of the actor initiating the action.
 * @param actorType Actor category ([AuditActorType]).
 * @param actorUserRole User role if applicable.
 * @param action Executed action ([AuditActionType]).
 * @param resource Target resource category ([AuditResourceType]).
 * @param resourceId Target resource ID.
 * @param resourceValueSensitivity Sensitivity level of resource value ([AuditValueSensitivity]).
 * @param status Outcome status ([AuditStatus]).
 * @param metadata Additional metadata entries.
 * @param message Human-readable log message.
 * @param createdAt Event timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestAuditEventInternal(
    id: AuditEventId = createTestAuditEventId(),
    actorId: String? = TEST_USER_ID,
    actorType: AuditActorType = AuditActorType.USER,
    actorUserRole: String? = "USER",
    action: AuditActionType = UserAuditActionType.LOGIN_BY_EMAIL,
    resource: AuditResourceType = CommonAuditResourceType.AUDIT_EVENT,
    resourceId: String? = TEST_USER_ID,
    resourceValueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE,
    status: AuditStatus = AuditStatus.SUCCESS,
    metadata: Set<AuditEventMetadata> = setOf(createTestAuditEventMetadata()),
    message: String? = "User logged in successfully",
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = null
) = AuditEventInternal(
    id = id,
    actorId = actorId,
    actorType = actorType,
    actorUserRole = actorUserRole,
    action = action,
    resource = resource,
    resourceId = resourceId,
    resourceValueSensitivity = resourceValueSensitivity,
    status = status,
    metadata = metadata,
    message = message,
    createdAt = createdAt,
    updatedAt = updatedAt
)
