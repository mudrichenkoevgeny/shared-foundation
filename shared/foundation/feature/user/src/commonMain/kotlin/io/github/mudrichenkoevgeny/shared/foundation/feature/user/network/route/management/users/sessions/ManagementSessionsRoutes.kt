package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementUsersNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes

/**
 * Route paths for user sessions in the management API (list, single read, revocation).
 */
object ManagementSessionsRoutes {
    /** Base path for global sessions management in the management API. */
    private const val BASE_MANAGEMENT_SESSIONS_ROUTE = "$BASE_MANAGEMENT_NETWORK_ROUTE/sessions"

    /** Base path for user-scoped sessions management. */
    private const val BASE_MANAGEMENT_USER_SESSIONS_ROUTE =
        "${ManagementUsersRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/sessions"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of active summary sessions based on filters.
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
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * - **Filters** ([UserFilterValues.UserSessionFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics
     *   (e.g., `?client_type=ANDROID&client_type=WASM`); different keys combine as **AND**.
     *   - [UserFilterValues.UserSessionFilterValues.USER_ID] — filter by user ID.
     *   - [UserFilterValues.UserSessionFilterValues.USER_ROLE] — filter by [UserRole] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — filter by substring pattern for identifier value.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — filter by credential record ID
     *     ([UserSessionPrivatePayload.identifierId]).
     *   - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — filter by [UserAuthProvider] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — filter by [ClientType] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — filter by user agent substring pattern.
     *   - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — filter by IP address.
     *   - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — filter by BCP 47 language tag.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — filter by opaque device ID.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME] — filter by device name substring pattern.
     *   - [UserFilterValues.UserSessionFilterValues.APP_VERSION] — filter by application version.
     *   - [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — filter by OS version.
     *
     * Response body: [PagedResult] of [UserSessionSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For sessions of [UserRole.USER], needs
     *   [SessionPermissionCode.SESSION_GET_OF_USER_MASKED] or
     *   [SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED] or
     *   [SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.GlobalSessionList]
     */
    const val GET_SESSIONS = BASE_MANAGEMENT_SESSIONS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of active summary sessions for a specific target user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
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
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * - **Filters** ([UserFilterValues.UserSessionFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics
     *   (e.g., `?client_type=ANDROID&client_type=WASM`); different keys combine as **AND**.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER] — filter by substring pattern for identifier value.
     *   - [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] — filter by credential record ID
     *     ([UserSessionPrivatePayload.identifierId]).
     *   - [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] — filter by [UserAuthProvider] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] — filter by [ClientType] serial name.
     *   - [UserFilterValues.UserSessionFilterValues.USER_AGENT] — filter by user agent substring pattern.
     *   - [UserFilterValues.UserSessionFilterValues.IP_ADDRESS] — filter by IP address.
     *   - [UserFilterValues.UserSessionFilterValues.LANGUAGE] — filter by BCP 47 language tag.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_ID] — filter by opaque device ID.
     *   - [UserFilterValues.UserSessionFilterValues.DEVICE_NAME] — filter by device name substring pattern.
     *   - [UserFilterValues.UserSessionFilterValues.APP_VERSION] — filter by application version.
     *   - [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] — filter by OS version.
     *
     * Response body: [PagedResult] of [UserSessionSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For sessions of [UserRole.USER], needs
     *   [SessionPermissionCode.SESSION_GET_OF_USER_MASKED] or
     *   [SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED] or
     *   [SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserSessionList]
     */
    const val GET_USER_SESSIONS = BASE_MANAGEMENT_USER_SESSIONS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific private session details.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * Response body: [UserSessionPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For sessions of [UserRole.USER], needs
     *   [SessionPermissionCode.SESSION_GET_OF_USER_MASKED] or
     *   [SessionPermissionCode.SESSION_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [SessionPermissionCode.SESSION_GET_OF_STAFF_MASKED]
     *   or [SessionPermissionCode.SESSION_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.SessionDetail]
     */
    const val GET_SESSION = "$BASE_MANAGEMENT_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes a specific session for the given session ID.
     *
     * Path parameter: [UserApiPaths.SESSION_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [SessionPermissionCode.SESSION_DELETE_FOR_USER] when owner has [UserRole.USER];
     *   [SessionPermissionCode.SESSION_DELETE_FOR_STAFF] when owner has [UserRole.STAFF] (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_DELETE_SESSION].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the revocation.
     * - **Resource:** [UserAuditResourceType.SESSION]. Set `resourceId` to the [UserApiPaths.SESSION_ID].
     * - **Metadata:** Include:
     *   - [ClientInfo] (see [CommonAuditMetadataKey]).
     *   - [UserAuditMetadataKey.USER_ID] — the owner of the session.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserSessionList]
     *   - [ManagementUsersNavigation.GlobalSessionList]
     *   - [ManagementUsersNavigation.SessionDetail]
     */
    const val DELETE_SESSION = "$BASE_MANAGEMENT_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes **all** active sessions for the specified user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [SessionPermissionCode.SESSION_DELETE_FOR_USER] when owner has [UserRole.USER];
     *   [SessionPermissionCode.SESSION_DELETE_FOR_STAFF] when owner has [UserRole.STAFF] (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_DELETE_ALL_USER_SESSIONS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the revocation.
     * - **Resource:** [UserAuditResourceType.SESSION]. Leave `resourceId` unset (bulk operation).
     * - **Metadata:** Include:
     *   - [ClientInfo] (see [CommonAuditMetadataKey]).
     *   - [UserAuditMetadataKey.USER_ID] — the account whose sessions were deleted.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserSessionList]
     */
    const val DELETE_ALL_USER_SESSIONS = BASE_MANAGEMENT_USER_SESSIONS_ROUTE
}