package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserAccountStatusRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserPermissionsRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Route paths for user management in the management API.
 */
object ManagementUserRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [CreateByEmailRequest].
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_CREATE_AS_USER] when the server creates an
     * account with [UserRole.USER]; [UserPermissionCode.USER_CREATE_AS_STAFF] when it creates [UserRole.STAFF].
     *
     * Response body: [UserDetailsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful creation and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_CREATE_USER] and resource [UserAuditResourceType.USER]. After success, set
     * `resourceId` to the new account id from [UserDetailsPayload].
     */
    const val CREATE_USER = BaseManagementUserRoutes.CREATE_USER

    /**
     * **HTTP method:** `GET`
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserSortBy.LAST_LOGIN_AT],
     *   [UserSortValues.UserSortBy.LAST_ACTIVE_AT],
     *   [UserSortValues.UserSortBy.CREATED_AT],
     *   [UserSortValues.UserSortBy.UPDATED_AT],
     *   [UserSortValues.UserSortBy.SCHEDULED_PERMANENT_DELETION_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserFilterValues], optional). If omitted, no filtering (all users matching caller
     * access, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [UserFilterValues.UserFilterValues.ROLE] — [UserRole] serial name ([UserDetailsPayload.role]).
     * - [UserFilterValues.UserFilterValues.ACCOUNT_STATUS] — [UserAccountStatus] serial name ([UserDetailsPayload.accountStatus]).
     * - [UserFilterValues.UserFilterValues.ACCOUNT_STATUS_BEFORE_DELETION] — [UserAccountStatus] serial name ([UserDetailsPayload.accountStatusBeforeDeletion]).
     *
     * **Authorization** ([UserPermissionCode]): result rows are limited to targets the caller may read —
     * [UserPermissionCode.USER_GET_OF_USER] for accounts with [UserRole.USER],
     * [UserPermissionCode.USER_GET_OF_STAFF] for accounts with [UserRole.STAFF] or [UserRole.ADMIN] (server aligns admin
     * profile reads with the staff-scoped grant).
     *
     * Response body: [PagedResult] of [UserDetailsPayload].
     */
    const val GET_USERS = BaseManagementUserRoutes.GET_USERS

    /**
     * **HTTP method:** `GET`
     *
     * Returns the user for the given identifier.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_GET_OF_USER] when the target has [UserRole.USER];
     * [UserPermissionCode.USER_GET_OF_STAFF] when the target has [UserRole.STAFF] or [UserRole.ADMIN] (server aligns
     * admin profile reads with the staff-scoped grant).
     *
     * Response body: [UserDetailsPayload].
     */
    const val GET_USER = BaseManagementUserRoutes.GET_USER

    /**
     * **HTTP method:** `PATCH`
     *
     * Sets the user's account status to the value in the JSON body.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: [UpdateUserAccountStatusRequest] — JSON object whose field [UserApiFields.ACCOUNT_STATUS] carries
     * the target status as a [UserAccountStatus] serial name on the wire (for example the string
     * [UserAccountStatus.STATUS_ACTIVE]), not the Kotlin enum constant name.
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_UPDATE_STATUS_FOR_USER] when the target has
     * [UserRole.USER]; [UserPermissionCode.USER_UPDATE_STATUS_FOR_STAFF] when the target has [UserRole.STAFF] or
     * [UserRole.ADMIN] (server aligns admin account updates with the staff-scoped grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS] and resource [UserAuditResourceType.USER]. Set
     * `resourceId` to the path [UserApiPaths.USER_ID].
     */
    const val UPDATE_USER_ACCOUNT_STATUS = BaseManagementUserRoutes.UPDATE_USER_ACCOUNT_STATUS

    /**
     * **HTTP method:** `PATCH`
     *
     * Replaces the user's explicit permission grants with the set in the JSON body.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * Request body: [UpdateUserPermissionsRequest] — JSON object with [UserApiFields.PERMISSIONS], an array of
     * permission code strings ([PermissionCode] on the wire).
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_USER] when the target
     * has [UserRole.USER]; [UserPermissionCode.USER_UPDATE_PERMISSIONS_FOR_STAFF] when the target has [UserRole.STAFF]
     * or [UserRole.ADMIN] (server aligns admin account updates with the staff-scoped grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_UPDATE_USER_PERMISSIONS] and resource [UserAuditResourceType.USER]. Set `resourceId`
     * to the path [UserApiPaths.USER_ID]. Avoid logging raw permission lists in metadata unless your retention policy
     * allows it.
     */
    const val UPDATE_USER_PERMISSIONS = BaseManagementUserRoutes.UPDATE_USER_PERMISSIONS

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the user account for the given [UserApiPaths.USER_ID].
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_DELETE_FOR_USER] when the target has
     * [UserRole.USER]; [UserPermissionCode.USER_DELETE_FOR_STAFF] when the target has [UserRole.STAFF] or
     * [UserRole.ADMIN] (server aligns admin account deletion with the staff-scoped grant if applicable).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful deletion and for security-relevant denials. Use action
     * [UserAuditActionType.MANAGEMENT_DELETE_USER] and resource [UserAuditResourceType.USER]. Set `resourceId` to the
     * path [UserApiPaths.USER_ID] before the account is removed.
     */
    const val DELETE_USER = BaseManagementUserRoutes.DELETE_USER
}
