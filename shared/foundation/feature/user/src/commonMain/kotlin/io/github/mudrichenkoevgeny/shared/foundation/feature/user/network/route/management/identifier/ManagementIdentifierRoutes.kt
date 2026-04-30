package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.identifier.BaseManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes

/**
 * Route paths for user identifiers in the management API (list, single read, delete).
 */
object ManagementIdentifierRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of identifiers.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     * [UserSortValues.UserIdentifierSortBy.CREATED_AT],
     * [UserSortValues.UserIdentifierSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     * [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([UserFilterValues.UserIdentifierFilterValues]): all filters are optional.
     * Same key repeated — **OR**; different keys — **AND**.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_ID] — list of user IDs.
     * - [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] — list of [UserAuthProvider] serial names.
     * - [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] — list of free-text identifiers; server-defined matching.
     *
     * Response body: [PagedResult] of [UserIdentifierPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** For identifiers owned by [UserRole.USER], needs
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED].
     * For [UserRole.STAFF], needs [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED].
     */
    const val GET_IDENTIFIERS = BaseManagementIdentifierRoutes.GET_IDENTIFIERS

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific identifier details.
     *
     * Path parameter: [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * Response body: [UserIdentifierPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** For identifiers owned by [UserRole.USER], needs
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_MASKED] or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_USER_UNMASKED].
     * For [UserRole.STAFF], needs [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_MASKED] or
     * [IdentifierPermissionCode.IDENTIFIER_GET_OF_STAFF_UNMASKED].
     */
    const val GET_IDENTIFIER = BaseManagementIdentifierRoutes.GET_IDENTIFIER

    /**
     * **HTTP method:** `DELETE`
     *
     * Removes the identifier record for the given user.
     *
     * Path parameters: [UserApiPaths.USER_ID], [UserApiPaths.USER_IDENTIFIER_ID].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_USER] when owner
     * has [UserRole.USER];
     * [IdentifierPermissionCode.IDENTIFIER_DELETE_FOR_STAFF] when owner has [UserRole.STAFF]
     * (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     * Actor cannot target own account.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled). Returns
     * [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_DELETE_IDENTIFIER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the deletion.
     * * **Resource:** [UserAuditResourceType.IDENTIFIER]. Set `resourceId` to the [UserApiPaths.USER_IDENTIFIER_ID].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.USER_ID] — the owner of the identifier.
     */
    const val DELETE_IDENTIFIER = BaseManagementIdentifierRoutes.DELETE_IDENTIFIER
}