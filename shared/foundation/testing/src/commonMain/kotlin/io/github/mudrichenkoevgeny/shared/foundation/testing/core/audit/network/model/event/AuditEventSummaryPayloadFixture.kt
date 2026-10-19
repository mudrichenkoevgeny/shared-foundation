package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.TEST_AUDIT_EVENT_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS

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
