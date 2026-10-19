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
import kotlin.time.Instant

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
