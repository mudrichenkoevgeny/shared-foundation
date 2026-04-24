package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseOpenUserRoutes

/**
 * Route paths for the authenticated user's account in the open API.
 */
object OpenUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserDetailsPayload].
     */
    const val GET_USER = BaseOpenUserRoutes.GET_USER

    /**
     * **HTTP method:** `DELETE`
     *
     * Schedules the user account for permanent deletion. The account will be
     * purged after a grace period.
     *
     * Response body: [UserDetailsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful deletion scheduling and security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_SCHEDULE_USER_DELETION].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val SCHEDULE_DELETION = BaseOpenUserRoutes.SCHEDULE_DELETION

    /**
     * **HTTP method:** `POST`
     *
     * Restores an account in pending-deletion state (cancels scheduled purge).
     *
     * Response body: [UserDetailsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful restore and security-relevant denials.
     * * **Action:** [UserAuditActionType.SELF_RESTORE_USER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] extracted from the token or [UserApiPaths.USER_ID].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] from [UserApiPaths.USER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val RESTORE_USER = BaseOpenUserRoutes.RESTORE_USER
}