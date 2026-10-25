package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes

/**
 * Route paths for the current authenticated principal's sessions in the management API (self-service).
 */
object SelfManagementSessionRoutes {
    /** Base path for self-service session operations in management API. */
    private const val BASE_SELF_SESSIONS_ROUTE =
        "${SelfManagementUserRoutes.BASE_SELF_MANAGEMENT_USER_ROUTE}/sessions"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes the current active management session.
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.LOGOUT].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the current session identifier.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.SessionList]
     */
    const val LOGOUT = "$BASE_SELF_SESSIONS_ROUTE/logout"

    /**
     * **HTTP method:** `GET`
     *
     * Returns active summary sessions for the **current** authenticated management account.
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
     * - **Filters** ([UserFilterValues.UserSessionFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics
     *   (e.g., `?client_type=ANDROID&client_type=WASM`); different keys combine as **AND**.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — filter by free-text identifier; server-defined.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — filter by credential record ID
     *     ([UserSessionPrivatePayload.identifierId]).
     *   - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — filter by [UserAuthProvider] serial name
     *     ([UserSessionPrivatePayload.identifierAuthProvider]).
     *   - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — filter by [ClientType] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — filter by free-text agent; server-defined.
     *   - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — filter by free-text IP address; server-defined.
     *   - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — filter by free-text language; server-defined.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — filter by opaque device ID.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME] — filter by free-text device name; server-defined.
     *   - [UserFilterValues.UserSessionFilterValues.APP_VERSION] — filter by application version.
     *   - [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — filter by free-text OS version;
     *     server-defined.
     *
     * Request body: None.
     * Response body: [PagedResult] of [UserSessionSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.SessionList]
     */
    const val GET_SESSIONS = BASE_SELF_SESSIONS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Returns private details of a specific session owned by the current management account.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Request body: None.
     * Response body: [UserSessionPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.SessionDetail]
     */
    const val GET_SESSION = "$BASE_SELF_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific session for the current management account.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_DELETE_SESSION].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the [UserSessionId]
     *   from [UserApiPaths.SESSION_ID].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.USER_ID] — the owner of the session.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.SessionList]
     *   - [ManagementSelfUserNavigation.SessionDetail]
     */
    const val DELETE_SESSION = "$BASE_SELF_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes all sessions for the current management account except the one used for this request.
     *
     * Request body: None.
     * Response body: [DeletedSessionsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_DELETE_OTHER_SESSIONS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.SESSION]. Leave `resourceId` unset (bulk operation).
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.USER_ID] — the account whose sessions are being deleted.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.SessionList]
     */
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SELF_SESSIONS_ROUTE/others"

    /**
     * **HTTP method:** `POST`
     *
     * Performs re-authentication via TOTP for the current management session to update its trust level.
     *
     * Request body: [VerifyTotpPayload].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.REAUTHENTICATE_SESSION].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the current [UserSessionId].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens: None.
     * - Flow: Global re-authentication dialog (no dedicated screen).
     */
    const val REAUTHENTICATE_SESSION = "$BASE_SELF_SESSIONS_ROUTE/reauthenticate"
}