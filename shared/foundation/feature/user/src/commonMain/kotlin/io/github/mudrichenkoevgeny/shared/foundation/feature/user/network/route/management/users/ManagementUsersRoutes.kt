package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementUsersNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.ManagementUpdateUserRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes

/**
 * Route paths for user management in the management API.
 */
object ManagementUsersRoutes {
    /**
     * Base path for user management operations under [CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE].
     */
    internal const val BASE_MANAGEMENT_USERS_ROUTE = "$BASE_MANAGEMENT_NETWORK_ROUTE/users"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of summary users based on filters.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of:
     *   - [UserSortBy.AUTHORITY_LEVEL]
     *   - [UserSortBy.LAST_LOGIN_AT]
     *   - [UserSortBy.LAST_ACTIVE_AT]
     *   - [UserSortBy.SCHEDULED_PERMANENT_DELETION_AT]
     *   - [UserSortBy.TEMPORARY_LOCKOUT_UNTIL]
     *   - [UserSortBy.NICKNAME]
     *   - [UserSortBy.DISPLAY_NAME]
     *   - [UserSortBy.BIRTHDATE]
     *   - [UserSortBy.CREATED_AT]
     *   - [UserSortBy.UPDATED_AT]
     * - [ListingParamNames.Sort.SORT_ORDER] — exactly one of:
     *   - [CommonApiFields.SortOrder.ASC]
     *   - [CommonApiFields.SortOrder.DESC]
     *
     * - **Filters** ([UserFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics; different keys combine as **AND**.
     *   - **Multi-value Include Filters** (repeated query keys, **OR** semantics):
     *     - [UserFilterValues.USER_ID] — filter by one or more specific user IDs.
     *     - [UserFilterValues.ROLE] — filter by one or more [UserRole] serial names.
     *     - [UserFilterValues.ACCOUNT_STATUS] — filter by one or more [UserAccountStatus] serial names.
     *     - [UserFilterValues.ACCOUNT_STATUS_ON_RESTORE] — filter by target [UserAccountStatus] serial names.
     *     - [UserFilterValues.PERMISSION_CODES] — filter by one or more explicit [PermissionCode] strings.
     *     - [UserFilterValues.ACCOUNT_LOCKOUT_TYPE] — filter by one or more [AccountLockoutType] serial names.
     *   - **Multi-value Exclude Filters** (repeated query keys, **NOT IN** semantics):
     *     - [UserFilterValues.EXCLUDE_ROLE] — exclude specific [UserRole] serial names.
     *     - [UserFilterValues.EXCLUDE_ACCOUNT_STATUS] — exclude specific [UserAccountStatus] serial names.
     *     - [UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE] — exclude target [UserAccountStatus] serial names.
     *     - [UserFilterValues.EXCLUDE_PERMISSION_CODES] — exclude specific [PermissionCode] strings.
     *     - [UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE] — exclude specific [AccountLockoutType] serial names.
     *   - **Exact Filters**:
     *     - [UserFilterValues.IS_TOTP_ENABLED] — boolean flag for TOTP 2FA status.
     *     - [UserFilterValues.NICKNAME] — filter by exact nickname handle.
     *     - [UserFilterValues.DISPLAY_NAME] — filter by exact display name.
     *   - **Presence Filters**:
     *     - [UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET] — filter by whether target status on restore is set.
     *     - [UserFilterValues.IS_LAST_LOGIN_AT_SET] — filter by whether user has logged in at least once.
     *     - [UserFilterValues.IS_LAST_ACTIVE_AT_SET] — filter by whether user has recorded activity.
     *     - [UserFilterValues.IS_UPDATED_AT_SET] — filter by whether user record has been updated.
     *     - [UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET] — filter by whether deletion is scheduled.
     *     - [UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET] — filter by whether temporary lockout is active.
     *     - [UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET] — filter by whether custom storage quota is assigned.
     *     - [UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET] — filter by whether custom max upload size is assigned.
     *     - [UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET] — filter by whether custom max pending uploads count is assigned.
     *     - [UserFilterValues.IS_STORAGE_CONFIGURATION_SET] — filter by whether storage configuration overrides exist.
     *     - [UserFilterValues.IS_NICKNAME_SET] — filter by whether user has a nickname set.
     *     - [UserFilterValues.IS_DISPLAY_NAME_SET] — filter by whether user has a display name set.
     *     - [UserFilterValues.IS_AVATAR_SET] — filter by whether user has an avatar image set.
     *     - [UserFilterValues.IS_BIRTHDATE_SET] — filter by whether user has a birthdate set.
     *   - **Range Bounds** (inclusive bounds):
     *     - [UserFilterValues.AUTHORITY_LEVEL_FROM] — inclusive lower bound for authority level.
     *     - [UserFilterValues.AUTHORITY_LEVEL_TO] — inclusive upper bound for authority level.
     *     - [UserFilterValues.LAST_LOGIN_AT_FROM] — inclusive lower bound last login timestamp.
     *     - [UserFilterValues.LAST_LOGIN_AT_TO] — inclusive upper bound last login timestamp.
     *     - [UserFilterValues.LAST_ACTIVE_AT_FROM] — inclusive lower bound last active timestamp.
     *     - [UserFilterValues.LAST_ACTIVE_AT_TO] — inclusive upper bound last active timestamp.
     *     - [UserFilterValues.CREATED_AT_FROM] — inclusive lower bound creation timestamp.
     *     - [UserFilterValues.CREATED_AT_TO] — inclusive upper bound creation timestamp.
     *     - [UserFilterValues.UPDATED_AT_FROM] — inclusive lower bound update timestamp.
     *     - [UserFilterValues.UPDATED_AT_TO] — inclusive upper bound update timestamp.
     *     - [UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM] — lower bound scheduled deletion timestamp.
     *     - [UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO] — upper bound scheduled deletion timestamp.
     *     - [UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM] — lower bound temporary lockout expiration timestamp.
     *     - [UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO] — upper bound temporary lockout expiration timestamp.
     *     - [UserFilterValues.STORAGE_QUOTA_BYTES_FROM] — lower bound custom storage quota in bytes.
     *     - [UserFilterValues.STORAGE_QUOTA_BYTES_TO] — upper bound custom storage quota in bytes.
     *     - [UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM] — lower bound custom max upload size in bytes.
     *     - [UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO] — upper bound custom max upload size in bytes.
     *     - [UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM] — lower bound custom max pending uploads count.
     *     - [UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO] — upper bound custom max pending uploads count.
     *     - [UserFilterValues.BIRTHDATE_FROM] — inclusive lower bound user birthdate.
     *     - [UserFilterValues.BIRTHDATE_TO] — inclusive upper bound user birthdate.
     *   - **Free-text Search**:
     *     - [UserFilterValues.SEARCH_QUERY] — free-text search query string targeting:
     *       - Nickname handle ([UserPrivate.nickname]).
     *       - Display name ([UserPrivate.displayName]).
     *
     * Request body: None.
     * Response body: [PagedResult] of [UserSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_GET_OF_USER] for [UserRole.USER] targets;
     *   [UserPermissionCode.USER_GET_OF_STAFF] for [UserRole.STAFF] targets (**AND** semantics).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserList]
     */
    const val GET_USERS = BASE_MANAGEMENT_USERS_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Creates a new user account.
     *
     * Request body: [CreateByEmailRequest].
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_CREATE_AS_USER] for [UserRole.USER] target;
     *   [UserPermissionCode.USER_CREATE_AS_STAFF] for [UserRole.STAFF] target (**AND** semantics).
     * - **Authority Level:** Actor cannot create a user with an authority level equal to
     *   or greater than their own.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_CREATE_USER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the operation.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the new account id.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.EMAIL_ADDRESS] — the email used for creation.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.CreateUser]
     */
    const val CREATE_USER = BASE_MANAGEMENT_USERS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific private user details.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: None.
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_GET_OF_USER] for [UserRole.USER] targets;
     *   [UserPermissionCode.USER_GET_OF_STAFF] for [UserRole.STAFF] targets (**AND** semantics).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserDetail]
     */
    const val GET_USER = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}"

    /**
     * **HTTP method:** `PATCH`
     *
     * Updates user details, status, or permissions.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: [ManagementUpdateUserRequest].
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** Required permissions depend on the fields provided in [ManagementUpdateUserRequest]
     *   and the target user's role (**AND** semantics for all provided fields):
     *   - If [ManagementUpdateUserRequest.accountStatus] is set:
     *     [UserPermissionCode.USER_UPDATE_STATUS_FOR_USER] (target: [UserRole.USER])
     *     [UserPermissionCode.USER_UPDATE_STATUS_FOR_STAFF] (target: [UserRole.STAFF])
     *   - If [ManagementUpdateUserRequest.authorityLevel] is set:
     *     [UserPermissionCode.USER_UPDATE_AUTHORITY_FOR_USER] (target: [UserRole.USER])
     *     [UserPermissionCode.USER_UPDATE_AUTHORITY_FOR_STAFF] (target: [UserRole.STAFF])
     *   - If [ManagementUpdateUserRequest.permissionCodes] is set:
     *     [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_USER] (target: [UserRole.USER])
     *     [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_STAFF] (target: [UserRole.STAFF])
     *   - If [ManagementUpdateUserRequest.lockoutType] or [ManagementUpdateUserRequest.temporaryLockoutUntil] is set:
     *     [UserPermissionCode.USER_UPDATE_STATUS_FOR_USER] (target: [UserRole.USER])
     *     [UserPermissionCode.USER_UPDATE_STATUS_FOR_STAFF] (target: [UserRole.STAFF])
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_UPDATE_USER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the update.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserApiPaths.USER_ID].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserDetail]
     */
    const val UPDATE_USER = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the specified user account.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: None.
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_DELETE_FOR_USER] for [UserRole.USER] target;
     *   [UserPermissionCode.USER_DELETE_FOR_STAFF] for [UserRole.STAFF] target (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_DELETE_USER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserApiPaths.USER_ID].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserDetail]
     */
    const val DELETE_USER = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}"
}