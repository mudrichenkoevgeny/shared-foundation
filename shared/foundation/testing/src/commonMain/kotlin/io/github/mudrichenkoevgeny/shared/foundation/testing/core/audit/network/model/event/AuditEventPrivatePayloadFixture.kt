package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.TEST_AUDIT_EVENT_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

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
