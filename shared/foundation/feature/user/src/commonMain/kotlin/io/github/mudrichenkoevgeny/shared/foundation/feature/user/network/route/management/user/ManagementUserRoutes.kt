package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes

/**
 * Route paths for user management in the management API.
 */
object ManagementUserRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Creates a new user account.
     *
     * Request body: [CreateByEmailRequest].
     *
     * Response body: [UserDetailsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_CREATE_AS_USER] for [UserRole.USER] target;
     * [UserPermissionCode.USER_CREATE_AS_STAFF] for [UserRole.STAFF] target (**AND** semantics).
     * - **Authority Level:** Actor cannot create a user with an authority level equal to
     * or greater than their own.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled). Returns
     * [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_CREATE_USER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the operation.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the new account id.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — the email used for creation.
     */
    const val CREATE_USER = BaseManagementUserRoutes.CREATE_USER

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of users based on filters.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     * [UserSortValues.UserSortBy.LAST_LOGIN_AT],
     * [UserSortValues.UserSortBy.LAST_ACTIVE_AT],
     * [UserSortValues.UserSortBy.SCHEDULED_PERMANENT_DELETION_AT],
     * [UserSortValues.UserSortBy.CREATED_AT],
     * [UserSortValues.UserSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserFilterValues]): all filters are optional.
     *
     * Response body: [PagedResult] of [UserDetailsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_GET_OF_USER] for [UserRole.USER] targets;
     * [UserPermissionCode.USER_GET_OF_STAFF] for [UserRole.STAFF] targets (**AND** semantics).
     */
    const val GET_USERS = BaseManagementUserRoutes.GET_USERS

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific user details.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Response body: [UserDetailsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_GET_OF_USER] for [UserRole.USER] targets;
     * [UserPermissionCode.USER_GET_OF_STAFF] for [UserRole.STAFF] targets (**AND** semantics).
     */
    const val GET_USER = BaseManagementUserRoutes.GET_USER

    /**
     * **HTTP method:** `PATCH`
     *
     * Updates user details, status, or permissions.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: [UpdateUserRequest].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** Required permissions depend on the fields provided in [UpdateUserRequest]
     * and the target user's role (**AND** semantics for all provided fields):
     * - If [UpdateUserRequest.accountStatus] is set:
     * - [UserPermissionCode.USER_UPDATE_STATUS_FOR_USER] (target: [UserRole.USER])
     * - [UserPermissionCode.USER_UPDATE_STATUS_FOR_STAFF] (target: [UserRole.STAFF])
     * - If [UpdateUserRequest.authorityLevel] is set:
     * - [UserPermissionCode.USER_UPDATE_AUTHORITY_FOR_USER] (target: [UserRole.USER])
     * - [UserPermissionCode.USER_UPDATE_AUTHORITY_FOR_STAFF] (target: [UserRole.STAFF])
     * - If [UpdateUserRequest.permissionCodes] is set:
     * - [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_USER] (target: [UserRole.USER])
     * - [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_STAFF] (target: [UserRole.STAFF])
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     * Actor cannot target own account.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled). Returns
     * [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_UPDATE_USER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the update.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserApiPaths.USER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val UPDATE_USER = BaseManagementUserRoutes.UPDATE_USER

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the specified user account.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_DELETE_FOR_USER] for [UserRole.USER] target;
     * [UserPermissionCode.USER_DELETE_FOR_STAFF] for [UserRole.STAFF] target (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     * Actor cannot target own account.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled). Returns
     * [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_DELETE_USER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserApiPaths.USER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val DELETE_USER = BaseManagementUserRoutes.DELETE_USER
}