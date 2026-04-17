package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session.BaseManagementSessionRoutes

/**
 * Route paths for user sessions in the management API (list, single read, revocation).
 */
object ManagementSessionRoutes {
    /**
     * **HTTP method:** `GET`
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
     * **Filters** ([UserFilterValues.UserSessionFilterValues]): [UserFilterValues.UserSessionFilterValues.USER_ID]
     * required; others optional. Same key repeated — **OR**; different keys — **AND**.
     *
     * - [UserFilterValues.UserSessionFilterValues.USER_ID] — list of user IDs.
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — list of free-text identifiers; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — list of credential record IDs ([UserSessionPayload.identifierId]).
     * - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names ([UserSessionPayload.identifierAuthProvider]).
     * - [UserFilterValues.UserSessionFilterValues.REVOKED] — `true` or `false`.
     * - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — list of [ClientType] serial names.
     * - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — list of free-text agents; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — list of free-text IP addresses; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — list of free-text languages; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — list of opaque device IDs.
     * - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME] — list of free-text device names; server-defined.
     * - [UserFilterValues.UserSessionFilterValues.APP_VERSION] — list of application versions.
     * - [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — list of free-text values; server-defined.
     *
     * **Authorization** ([SessionPermissionCode]): for sessions of an account with [UserRole.USER], caller needs
     * [SessionPermissionCode.SESSION_GET_OF_USER_MASKED] and/or [SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED];
     * for [UserRole.STAFF], [SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED] and/or
     * [SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED]. Per-row [UserSessionPayload.isSensitiveValuesMasked]
     * reflects the caller's effective access. No constant in [SessionPermissionCode] grants reading sessions whose owner
     * has [UserRole.ADMIN].
     *
     * Response body: [PagedResult] of [UserSessionPayload].
     */
    const val GET_USER_SESSIONS = BaseManagementSessionRoutes.GET_USER_SESSIONS

    /**
     * **HTTP method:** `GET`
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * **Authorization** ([SessionPermissionCode]): for sessions of an account with [UserRole.USER], caller needs
     * [SessionPermissionCode.SESSION_GET_OF_USER_MASKED] and/or [SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED];
     * for [UserRole.STAFF], [SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED] and/or
     * [SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED]. [UserSessionPayload.isSensitiveValuesMasked] matches the
     * caller's effective access for that session's owner. No constant in [SessionPermissionCode] grants reading sessions
     * whose owner has [UserRole.ADMIN].
     *
     * Response body: [UserSessionPayload].
     */
    const val GET_USER_SESSION = BaseManagementSessionRoutes.GET_USER_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Force-terminates the session [UserApiPaths.SESSION_ID] for the user [UserApiPaths.USER_ID].
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     *
     * **Authorization:** [SessionPermissionCode.SESSION_DELETE_FOR_USER] when the session owner has [UserRole.USER];
     * [SessionPermissionCode.SESSION_DELETE_FOR_STAFF] when the owner has [UserRole.STAFF] (server aligns [UserRole.ADMIN]
     * accounts with the staff-scoped delete grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful termination and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_DELETE_USER_SESSION] and resource [UserAuditResourceType.USER_SESSION]. Set
     * `resourceId` to the path [UserApiPaths.SESSION_ID]. Add metadata [UserAuditMetadataKey.USER_ID] with the path
     * [UserApiPaths.USER_ID] (account whose session was terminated).
     */
    const val DELETE_USER_SESSION = BaseManagementSessionRoutes.DELETE_USER_SESSION

    /**
     * **HTTP method:** `DELETE`
     *
     * Force-terminates **all** sessions for the user [UserApiPaths.USER_ID].
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization:** [SessionPermissionCode.SESSION_DELETE_FOR_USER] when the user identified by [UserApiPaths.USER_ID]
     * has [UserRole.USER]; [SessionPermissionCode.SESSION_DELETE_FOR_STAFF] when that user has [UserRole.STAFF] (server
     * aligns [UserRole.ADMIN] accounts with the staff-scoped delete grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful termination and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_DELETE_ALL_USER_SESSIONS] and resource [UserAuditResourceType.USER_SESSION]. Leave
     * `resourceId` unset (bulk operation). Add metadata [UserAuditMetadataKey.USER_ID] with the path [UserApiPaths.USER_ID].
     */
    const val DELETE_ALL_USER_SESSIONS = BaseManagementSessionRoutes.DELETE_ALL_USER_SESSIONS
}
