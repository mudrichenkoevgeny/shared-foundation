package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes.BASE_USER_ROUTE

/**
 * Route paths for session management in the open API.
 */
public object OpenSessionRoutes {
    /** Base path for open session management operations. */
    private const val BASE_SESSIONS_ROUTE = "$BASE_USER_ROUTE/sessions"

    /**
     * **HTTP method:** `POST`
     *
     * Deletes the current active session.
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
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
     *   - [ClientAuthNavigation.PendingDeletion]
     *   - [ClientUserNavigation.SessionList]
     */
    public const val LOGOUT: String = "$BASE_SESSIONS_ROUTE/logout"

    /**
     * **HTTP method:** `GET`
     *
     * Returns active summary sessions for the **current** authenticated user (self-service).
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of:
     *   - [UserSessionSortBy.EXPIRES_AT]
     *   - [UserSessionSortBy.LAST_ACCESSED_AT]
     *   - [UserSessionSortBy.LAST_REAUTHENTICATED_AT]
     *   - [UserSessionSortBy.CREATED_AT]
     *   - [UserSessionSortBy.UPDATED_AT]
     * - [ListingParamNames.Sort.SORT_ORDER] — exactly one of:
     *   - [CommonApiFields.SortOrder.ASC]
     *   - [CommonApiFields.SortOrder.DESC]
     *
     * - **Filters** ([UserSessionFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics; different keys combine as **AND**.
     *   - **Multi-value Include Filters** (repeated query keys, **OR** semantics):
     *     - [UserSessionFilterValues.SESSION_ID] — filter by one or more specific session IDs.
     *     - [UserSessionFilterValues.IDENTIFIER_ID] — filter by one or more login identifier IDs.
     *     - [UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER] — filter by one or more [UserAuthProvider] serial names.
     *     - [UserSessionFilterValues.CLIENT_TYPE] — filter by one or more [ClientType] serial names.
     *   - **Multi-value Exclude Filters** (repeated query keys, **NOT IN** semantics):
     *     - [UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER] — exclude [UserAuthProvider] serial names.
     *   - **Exact Filters**:
     *     - [UserSessionFilterValues.IDENTIFIER] — filter by exact raw or masked login identifier string.
     *     - [UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME] — filter by exact identifier display name.
     *     - [UserSessionFilterValues.DEVICE_ID] — filter by exact stable device ID.
     *     - [UserSessionFilterValues.DEVICE_NAME] — filter by exact device name.
     *     - [UserSessionFilterValues.APP_VERSION] — filter by exact application version.
     *     - [UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — filter by exact operating system version.
     *     - [UserSessionFilterValues.USER_AGENT] — filter by exact HTTP User-Agent string.
     *     - [UserSessionFilterValues.IP_ADDRESS] — filter by exact raw or masked IP address.
     *   - **Presence Filters**:
     *     - [UserSessionFilterValues.IS_USER_AGENT_SET] — filter by whether User-Agent header is present.
     *     - [UserSessionFilterValues.IS_IP_ADDRESS_SET] — filter by whether IP address is present.
     *     - [UserSessionFilterValues.IS_UPDATED_AT_SET] — filter by whether record has been updated.
     *   - **Range Bounds** (inclusive bounds):
     *     - [UserSessionFilterValues.EXPIRES_AT_FROM] — inclusive lower bound expiration timestamp.
     *     - [UserSessionFilterValues.EXPIRES_AT_TO] — inclusive upper bound expiration timestamp.
     *     - [UserSessionFilterValues.LAST_ACCESSED_AT_FROM] — inclusive lower bound last accessed timestamp.
     *     - [UserSessionFilterValues.LAST_ACCESSED_AT_TO] — inclusive upper bound last accessed timestamp.
     *     - [UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM] — lower bound re-authentication timestamp.
     *     - [UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO] — upper bound re-authentication timestamp.
     *     - [UserSessionFilterValues.CREATED_AT_FROM] — inclusive lower bound creation timestamp.
     *     - [UserSessionFilterValues.CREATED_AT_TO] — inclusive upper bound creation timestamp.
     *     - [UserSessionFilterValues.UPDATED_AT_FROM] — inclusive lower bound update timestamp.
     *     - [UserSessionFilterValues.UPDATED_AT_TO] — inclusive upper bound update timestamp.
     *   - **Free-text Search**:
     *     - [UserSessionFilterValues.SEARCH_QUERY] — free-text search query string targeting:
     *       - Raw or masked login identifier ([UserSessionPrivate.identifier]).
     *       - Identifier display name ([UserSessionPrivate.identifierDisplayName]).
     *       - Device name ([ClientDeviceInfo.deviceName]).
     *
     * Request body: None.
     * Response body: [PagedResult] of [UserSessionSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.SessionList]
     */
    public const val GET_SESSIONS: String = BASE_SESSIONS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific private session details by its identifier.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Request body: None.
     * Response body: [UserSessionPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientUserNavigation.SessionDetail]
     */
    public const val GET_SESSION: String = "$BASE_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the session for the given identifier.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
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
     *   - [ClientUserNavigation.SessionList]
     *   - [ClientUserNavigation.SessionDetail]
     */
    public const val DELETE_SESSION: String = "$BASE_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes all sessions for the current user except the one used for this request.
     *
     * Request body: None.
     * Response body: [DeletedSessionsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
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
     *   - [ClientUserNavigation.SessionList]
     */
    public const val DELETE_ALL_OTHER_SESSIONS: String = "$BASE_SESSIONS_ROUTE/others"

    /**
     * **HTTP method:** `POST`
     *
     * Performs re-authentication via TOTP to update the session's trust level.
     *
     * Request body: [VerifyTotpPayload].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
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
    public const val REAUTHENTICATE_SESSION: String = "$BASE_SESSIONS_ROUTE/reauthenticate"
}
