package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseUserRoutes

/**
 * Route paths for the authenticated user's account in the open API.
 */
object OpenUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserDetailsPayload].
     */
    const val GET_USER = BaseUserRoutes.GET_USER

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the user account.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful deletion scheduling and for security-relevant denials. Use
     * action [UserAuditActionType.SELF_DELETE_USER] and resource [UserAuditResourceType.USER]. Set `resourceId` to the path
     * [UserApiPaths.USER_ID].
     */
    const val DELETE_USER = BaseUserRoutes.DELETE_USER

    /**
     * **HTTP method:** `POST`
     *
     * Restores an account in pending-deletion state (cancels scheduled purge).
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful restore and for security-relevant denials. Use action
     * [UserAuditActionType.SELF_RESTORE_USER] and resource [UserAuditResourceType.USER]. Set `resourceId` to the path
     * [UserApiPaths.USER_ID].
     */
    const val RESTORE_USER = BaseUserRoutes.RESTORE_USER
}
