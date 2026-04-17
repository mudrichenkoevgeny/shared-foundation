package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseSessionRoutes

/**
 * Route paths for session management in the open API.
 */
object OpenSessionRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Terminates the current active session.
     */
    const val LOGOUT = BaseSessionRoutes.LOGOUT

    /**
     * **HTTP method:** `GET`
     *
     * Returns active sessions for the **current** authenticated user (self-service).
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserSessionSortBy.LAST_ACCESSED_AT],
     *   [UserSortValues.UserSessionSortBy.LAST_REAUTHENTICATED_AT],
     *   [UserSortValues.UserSessionSortBy.EXPIRES_AT],
     *   [UserSortValues.UserSessionSortBy.CREATED_AT],
     *   [UserSortValues.UserSessionSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserSessionFilterValues], optional). If omitted, no filtering (all sessions for
     * the current user, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names ([UserSessionPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.REVOKED] — true or false.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — opaque device id; free-tex.
     *
     * Response body: [PagedResult] of [UserSessionPayload] with [UserSessionPayload.isSensitiveValuesMasked] `false`.
     */
    const val GET_SESSIONS = BaseSessionRoutes.GET_SESSIONS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Response body: [UserSessionPayload] with [UserSessionPayload.isSensitiveValuesMasked] `false`.
     */
    const val GET_SESSION = BaseSessionRoutes.GET_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Terminates the session for the given identifier.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful revocation and for security-relevant denials. Use action
     * [UserAuditActionType.SELF_REVOKE_SESSION] and resource [UserAuditResourceType.USER_SESSION]. Set `resourceId` to the
     * path [UserApiPaths.SESSION_ID]. Add metadata [UserAuditMetadataKey.USER_ID] for the affected account when it differs
     * from the caller context in your deployment.
     */
    const val DELETE_SESSION = BaseSessionRoutes.DELETE_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Terminates all sessions for the current user except the one used for this request.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful bulk revocation and for security-relevant denials. Use
     * action [UserAuditActionType.SELF_REVOKE_OTHER_SESSIONS] and resource [UserAuditResourceType.USER_SESSION]. Leave
     * `resourceId` unset (bulk operation). Add metadata [UserAuditMetadataKey.USER_ID] when the subject must be explicit
     * for tooling.
     */
    const val DELETE_ALL_OTHER_SESSIONS = BaseSessionRoutes.DELETE_ALL_OTHER_SESSIONS
}
