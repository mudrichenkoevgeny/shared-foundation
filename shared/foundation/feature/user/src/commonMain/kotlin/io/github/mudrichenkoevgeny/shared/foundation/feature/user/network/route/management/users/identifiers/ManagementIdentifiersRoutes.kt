package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.management.BaseManagementRoutes.BASE_MANAGEMENT_ROUTE
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementUsersNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes

/**
 * Route paths for user identifiers in the management API (list, single read, delete).
 */
object ManagementIdentifiersRoutes {
    /** Base path for global user identifiers in the management API. */
    private const val BASE_MANAGEMENT_IDENTIFIERS_ROUTE = "$BASE_MANAGEMENT_ROUTE/identifiers"

    /** Base path for user-scoped identifiers management. */
    private const val BASE_MANAGEMENT_USER_IDENTIFIERS_ROUTE =
        "${ManagementUsersRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/identifiers"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of summary identifiers across all users.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): all filters are optional.
     * Same key repeated — **OR**; different keys combine as **AND**.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_ID] — list of user IDs.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names.
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — list of substring patterns for identifier values.
     *
     * Response body: [PagedResult] of [UserIdentifierSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For identifiers owned by [UserRole.USER], needs
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.GlobalIdentifierList]
     */
    const val GET_IDENTIFIERS = BASE_MANAGEMENT_IDENTIFIERS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of summary identifiers for a specific target user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     *   [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): all filters are optional.
     * Same key repeated — **OR**; different keys combine as **AND**.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names.
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — list of substring patterns for identifier values.
     *
     * Response body: [PagedResult] of [UserIdentifierSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For identifiers owned by [UserRole.USER], needs
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserIdentifierList]
     */
    const val GET_USER_IDENTIFIERS = BASE_MANAGEMENT_USER_IDENTIFIERS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific private identifier details.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Response body: [UserIdentifierPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** For identifiers owned by [UserRole.USER], needs
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED].
     *   For [UserRole.STAFF], needs [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] or
     *   [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.IdentifierDetail]
     */
    const val GET_IDENTIFIER = "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier record for the given identifier ID.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_USER] when owner
     *   has [UserRole.USER];
     *   [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_STAFF] when owner has [UserRole.STAFF]
     *   (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_DELETE_IDENTIFIER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * - **Metadata:** Include:
     *   - [ClientInfo] (see [CommonAuditMetadataKey]).
     *   - [UserAuditMetadataKey.USER_ID] — the owner of the identifier.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserIdentifierList]
     *   - [ManagementUsersNavigation.GlobalIdentifierList]
     *   - [ManagementUsersNavigation.IdentifierDetail]
     */
    const val DELETE_IDENTIFIER = "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

    /**
     * **HTTP method:** `DELETE`
     *
     * Revokes and removes the password credential from the specified identifier record.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [IdentifierPermissionCode.IDENTIFIER_DELETE_PASSWORD_FOR_USER] when owner has [UserRole.USER];
     *   [IdentifierPermissionCode.IDENTIFIER_DELETE_PASSWORD_FOR_STAFF] when owner has [UserRole.STAFF] (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     *   Actor cannot target own account.
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_DELETE_IDENTIFIER_PASSWORD].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the password revocation.
     * - **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * - **Metadata:** Include:
     *   - [ClientInfo] (see [CommonAuditMetadataKey]).
     *   - [UserAuditMetadataKey.USER_ID] — the owner of the identifier.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementUsersNavigation.UserIdentifierList]
     *   - [ManagementUsersNavigation.GlobalIdentifierList]
     *   - [ManagementUsersNavigation.IdentifierDetail]
     */
    const val DELETE_IDENTIFIER_PASSWORD =
        "$BASE_MANAGEMENT_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}/password"
}